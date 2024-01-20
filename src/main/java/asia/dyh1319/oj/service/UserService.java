package asia.dyh1319.oj.service;

import asia.dyh1319.oj.model.dto.user.UserLoginWithEmailRequest;
import asia.dyh1319.oj.model.dto.user.UserLoginWithPasswordRequest;
import asia.dyh1319.oj.model.dto.user.UserRegisterRequest;
import asia.dyh1319.oj.model.dto.user.UserRequestEmailCodeResponse;
import asia.dyh1319.oj.model.entity.User;
import asia.dyh1319.oj.model.vo.LoginUserVO;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
 */
public interface UserService extends IService<User> {
    
    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册请求体
     * @return 新用户 id
     */
    long userRegister(UserRegisterRequest userRegisterRequest);
    
    /**
     *
     * @param userAccount 待校验的用户账号
     * @return 是否合法
     */
    boolean userRegisterCheckUserAccount(String userAccount);
    
    /**
     * 用户请求服务器发送邮箱验证码（注册时使用）
     *
     * @param email 用户输入的邮箱地址
     * @return expireTime: 验证码过期时间（单位：秒）; intervalTime: 距离下一次该用户可以发送验证码的间隔时间（单位：秒）
     */
    UserRequestEmailCodeResponse userRequestEmailCode(String email);
    
    /**
     * 用户登录（账号密码登录）
     *
     * @param userLoginWithPasswordRequest  用户登录请求体（账号密码登录）
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLoginWithPassword(UserLoginWithPasswordRequest userLoginWithPasswordRequest, HttpServletRequest request);
    
    /**
     * 用户登录（邮箱验证码登录）
     *
     * @param UserLoginWithEmailRequest  用户登录请求体（邮箱验证码登录）
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLoginWithEmail(UserLoginWithEmailRequest UserLoginWithEmailRequest, HttpServletRequest request);
    
    /**
     * 用户请求服务器发送邮箱验证码（登录时使用）
     *
     * @param email 用户输入的邮箱地址
     * @return expireTime: 验证码过期时间（单位：秒）; intervalTime: 距离下一次该用户可以发送验证码的间隔时间（单位：秒）
     */
    UserRequestEmailCodeResponse userLoginRequestEmailCode(String email);
    
    /**
     * 用户注销
     */
    boolean userLogout(HttpServletRequest request);
    
    /**
     * 获取当前登录用户
     */
    User userGetLogin(HttpServletRequest request);
    
    /**
     * 获取脱敏的已登录用户信息
     */
    LoginUserVO getLoginUserVO(User user);
}
