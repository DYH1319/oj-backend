package asia.dyh1319.oj.service.impl;

import asia.dyh1319.oj.common.StatusCode;
import asia.dyh1319.oj.constant.UserConstant;
import asia.dyh1319.oj.exception.BusinessException;
import asia.dyh1319.oj.manager.RedisClient;
import asia.dyh1319.oj.mapper.UserMapper;
import asia.dyh1319.oj.model.dto.user.UserLoginWithEmailRequest;
import asia.dyh1319.oj.model.dto.user.UserLoginWithPasswordRequest;
import asia.dyh1319.oj.model.dto.user.UserRegisterRequest;
import asia.dyh1319.oj.model.dto.user.UserRequestEmailCodeResponse;
import asia.dyh1319.oj.model.entity.User;
import asia.dyh1319.oj.model.vo.LoginUserVO;
import asia.dyh1319.oj.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

/**
 * 用户服务实现
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    @Resource
    private UserMapper userMapper;
    
    @Resource
    private RedisClient redisClient;
    
    @Resource
    private JavaMailSender javaMailSender;
    
    @Override
    public long userRegister(UserRegisterRequest userRegisterRequest) {
        // 获取具体请求内容
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String email = userRegisterRequest.getEmail();
        String emailVerificationCode = userRegisterRequest.getEmailVerificationCode();
        
        // 校验非null非空字符串
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, email, emailVerificationCode)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "注册请求存在内容为空");
        }
        // 校验用户账号是否合法
        if (!userAccountRegexCheck(userAccount)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "账号只能由6~12位的大写字母、小写字母、阿拉伯数字和下划线_组成");
        }
        // 校验用户密码和确认密码是否合法
        if (!userPasswordRegexCheck(userPassword)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "密码只能由6~16位的大写字母、小写字母、阿拉伯数字和!@#$%^&*这八个特殊字符组成");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "确认密码与密码不一致");
        }
        // 校验邮箱的合法性
        if (!emailRegexCheck(email)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "邮箱地址不合法");
        }
        // 校验用户账号是否已经被注册使用了
        if (userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserAccount, userAccount)) != null) {
            throw new BusinessException(StatusCode.OPERATION_ERROR, "账号已经被注册使用了");
        }
        // 校验邮箱是否已经被注册使用了
        if (userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email)) != null) {
            throw new BusinessException(StatusCode.OPERATION_ERROR, "邮箱已经被注册使用了");
        }
        // 校验邮箱验证码是否正确
        if (emailVerificationCode.length() != 6 || !emailVerificationCode.equals(redisClient.get(emailSuffixWithExpireTime(email)))) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "邮箱验证码不正确");
        }
        
        // 清除邮箱验证码缓存
        if (!redisClient.delete(emailSuffixWithExpireTime(email))) {
            throw new BusinessException(StatusCode.SYSTEM_ERROR);
        }
        
        // SHA256加密密码
        String encryptPassword = sha256Encrypt(userPassword);
        
        // 写入数据库
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setEmail(email);
        // 初始用户昵称与用户账号保持一致，可通过其他方式修改
        user.setUsername(userAccount);
        
        int count = userMapper.insert(user);
        if (count != 1) {
            throw new BusinessException(StatusCode.SYSTEM_ERROR);
        }
        return user.getId();
    }
    
    @Override
    public boolean userRegisterCheckUserAccount(String userAccount) {
        if (StringUtils.isBlank(userAccount)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "用户账号为空");
        }
        // 校验用户账号是否合法
        if (!userAccountRegexCheck(userAccount)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "账号只能由6~12位的大写字母、小写字母、阿拉伯数字和下划线_组成");
        }
        // 校验用户账号是否已经被注册使用了
        if (userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserAccount, userAccount)) != null) {
            throw new BusinessException(StatusCode.OPERATION_ERROR, "账号已经被注册使用了");
        }
        return true;
    }
    
    @Override
    public UserRequestEmailCodeResponse userRequestEmailCode(String email) {
        // 校验邮箱是否合法
        if (StringUtils.isBlank(email)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "邮箱地址为空");
        }
        if (!emailRegexCheck(email)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "邮箱地址不合法");
        }
        
        // 校验邮箱是否已经被注册使用了
        if (userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email)) != null) {
            throw new BusinessException(StatusCode.OPERATION_ERROR, "邮箱已经被注册使用了");
        }
        
        // 检查Redis中的间隔发送时间是否过期
        String code = generateVerificationCode();
        String emailSuffixWithIntervalTime = emailSuffixWithIntervalTime(email);
        if (redisClient.get(emailSuffixWithIntervalTime) != null) {
            throw new BusinessException(StatusCode.OPERATION_ERROR, "请勿频繁请求验证码");
        }
        
        // 使用QQ邮箱的SMTP服务发送验证码
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dyh1319@qq.com");
        message.setTo(email);
        message.setSubject("Online Judge 注册验证码");
        message.setText("【验证码】您的注册验证码为：" + code + "。（5分钟内有效）如果这不是您本人的注册请求，请忽略此邮件。");
        javaMailSender.send(message);
        
        int expireTime = 300;
        int intervalTime = 60;
        
        // 设置验证码，有效期5分钟
        redisClient.set(emailSuffixWithExpireTime(email), code, expireTime);
        // 设置重发间隔，1分钟后可重发
        redisClient.set(emailSuffixWithIntervalTime, code, intervalTime);
        
        return new UserRequestEmailCodeResponse(expireTime, intervalTime);
    }
    
    @Override
    public LoginUserVO userLoginWithPassword(UserLoginWithPasswordRequest userLoginWithPasswordRequest, HttpServletRequest request) {
        String userAccount = userLoginWithPasswordRequest.getUserAccount();
        String userPassword = userLoginWithPasswordRequest.getUserPassword();
        
        // 校验非null非空字符串
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "登录请求存在内容为空（账号密码登录）");
        }
        // 校验用户账号是否合法
        if (!userAccountRegexCheck(userAccount)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "账号只能由6~12位的大写字母、小写字母、阿拉伯数字和下划线_组成");
        }
        // 校验用户密码是否合法
        if (!userPasswordRegexCheck(userPassword)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "密码只能由6~16位的大写字母、小写字母、阿拉伯数字和!@#$%^&*这八个特殊字符组成");
        }
        // 比对数据库
        if (userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserAccount, userAccount)) == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "账号不存在");
        }
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserAccount, userAccount).eq(User::getUserPassword, sha256Encrypt(userPassword)));
        if (user == null) {
            throw new BusinessException(StatusCode.OPERATION_ERROR, "账号密码不匹配");
        }
        // 记录用户登录态
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, user);
        // 返回登录用户视图（脱敏）
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        return loginUserVO;
    }
    
    @Override
    public LoginUserVO userLoginWithEmail(UserLoginWithEmailRequest UserLoginWithEmailRequest, HttpServletRequest request) {
        String email = UserLoginWithEmailRequest.getEmail();
        String emailVerificationCode = UserLoginWithEmailRequest.getEmailVerificationCode();
        
        // 校验非null非空字符串
        if (StringUtils.isAnyBlank(email, emailVerificationCode)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "登录请求存在内容为空（邮箱验证码登录）");
        }
        // 校验邮箱的合法性
        if (!emailRegexCheck(email)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "邮箱地址不合法");
        }
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
        if (user == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "邮箱地址不存在");
        }
        // 校验邮箱验证码是否正确
        if (emailVerificationCode.length() != 6 || !emailVerificationCode.equals(redisClient.get(emailSuffixWithExpireTime(email)))) {
            throw new BusinessException(StatusCode.OPERATION_ERROR, "邮箱验证码不正确");
        }
        // 清除邮箱验证码缓存
        if (!redisClient.delete(emailSuffixWithExpireTime(email))) {
            throw new BusinessException(StatusCode.SYSTEM_ERROR);
        }
        // 记录用户登录态
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, user);
        // 返回登录用户视图（脱敏）
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        return loginUserVO;
    }
    
    @Override
    public UserRequestEmailCodeResponse userLoginRequestEmailCode(String email) {
        // 校验邮箱是否合法
        if (StringUtils.isBlank(email)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "邮箱地址为空");
        }
        if (!emailRegexCheck(email)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "邮箱地址不合法");
        }
        
        // 校验邮箱是否存在
        if (userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email)) == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "邮箱地址不存在");
        }
        
        // 检查Redis中的间隔发送时间是否过期
        String code = generateVerificationCode();
        String emailSuffixWithIntervalTime = emailSuffixWithIntervalTime(email);
        if (redisClient.get(emailSuffixWithIntervalTime) != null) {
            throw new BusinessException(StatusCode.OPERATION_ERROR, "请勿频繁请求验证码");
        }
        
        // 使用QQ邮箱的SMTP服务发送验证码
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dyh1319@qq.com");
        message.setTo(email);
        message.setSubject("Online Judge 登录验证码");
        message.setText("【验证码】您的登录验证码为：" + code + "。（5分钟内有效）如果这不是您本人的登录请求，请忽略此邮件。");
        javaMailSender.send(message);
        
        int expireTime = 300;
        int intervalTime = 60;
        
        // 设置验证码，有效期5分钟
        redisClient.set(emailSuffixWithExpireTime(email), code, expireTime);
        // 设置重发间隔，1分钟后可重发
        redisClient.set(emailSuffixWithIntervalTime, code, intervalTime);
        
        return new UserRequestEmailCodeResponse(expireTime, intervalTime);
    }
    
    @Override
    public boolean userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return true;
    }
    
    @Override
    public LoginUserVO userGetLogin(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User currentUser = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getId, user.getId()));
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR);
        }
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, currentUser);
        return LoginUserVO.objToVo(currentUser);
    }
    
    /**
     * 随机生成6位阿拉伯数字验证码
     */
    private String generateVerificationCode() {
        int min = 0;
        int max = 999999;
        
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        return String.format("%06d", randomNum);
    }
    
    /**
     * 邮箱正则表达式校验
     */
    private boolean emailRegexCheck(String email) {
        return Pattern.compile("^[A-Za-z0-9+_.-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,8}$").matcher(email).matches();
    }
    
    /**
     * 用户账号正则表达式校验
     */
    private boolean userAccountRegexCheck(String userAccount) {
        return Pattern.compile("^[A-Za-z0-9_]{6,12}$").matcher(userAccount).matches();
    }
    
    /**
     * 用户密码正则表达式校验
     */
    private boolean userPasswordRegexCheck(String userPassword) {
        return Pattern.compile("^[A-Za-z0-9!@#$%^&*]{6,16}$").matcher(userPassword).matches();
    }
    
    /**
     * 为email地址加上" # Expire Time"后缀（不包含双引号）
     */
    private String emailSuffixWithExpireTime(String email) {
        return email + " # Expire Time";
    }
    
    /**
     * 为email地址加上" # Interval Time"后缀（不包含双引号）
     */
    private String emailSuffixWithIntervalTime(String email) {
        return email + " # Interval Time";
    }
    
    /**
     * SHA256加密
     * @param text 待加密的文本
     * @return 加密后的文本
     */
    private String sha256Encrypt(String text) {
        return DigestUtils.sha256Hex(text);
    }
}
