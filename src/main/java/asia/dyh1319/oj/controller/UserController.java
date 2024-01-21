package asia.dyh1319.oj.controller;

import asia.dyh1319.oj.annotation.AuthCheck;
import asia.dyh1319.oj.common.BaseResponse;
import asia.dyh1319.oj.common.StatusCode;
import asia.dyh1319.oj.exception.BusinessException;
import asia.dyh1319.oj.model.dto.user.UserLoginWithEmailRequest;
import asia.dyh1319.oj.model.dto.user.UserLoginWithPasswordRequest;
import asia.dyh1319.oj.model.dto.user.UserRegisterRequest;
import asia.dyh1319.oj.model.dto.user.UserRequestEmailCodeResponse;
import asia.dyh1319.oj.model.enums.UserRoleEnum;
import asia.dyh1319.oj.model.vo.LoginUserVO;
import asia.dyh1319.oj.service.UserService;
import asia.dyh1319.oj.utils.ResponseUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * user 控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Resource
    private UserService userService;
    
    /**
     * 用户注册
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "注册请求为空");
        }
        long result = userService.userRegister(userRegisterRequest);
        return ResponseUtils.success(result, "注册成功");
    }
    
    /**
     * 用户注册时校验当前注册的账号是否合法
     */
    @PostMapping("/register/check/userAccount")
    public BaseResponse<Boolean> userRegisterCheckUserAccount(@RequestBody(required = false) String userAccount) {
        if (StringUtils.isBlank(userAccount)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "用户账号为空");
        }
        boolean result = userService.userRegisterCheckUserAccount(userAccount);
        return ResponseUtils.success(result, "账号验证通过");
    }
    
    /**
     * 用户请求服务器发送邮箱验证码（注册使用）
     */
    @PostMapping("/register/request/email/code")
    public BaseResponse<UserRequestEmailCodeResponse> userRegisterRequestEmailCode(@RequestBody(required = false) String email) {
        if (StringUtils.isBlank(email)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "邮箱地址为空");
        }
        UserRequestEmailCodeResponse result = userService.userRequestEmailCode(email);
        return ResponseUtils.success(result, "验证码已发送，请注意查收");
    }
    
    /**
     * 用户登录（使用账号密码登录）
     */
    @PostMapping("/login/password")
    public BaseResponse<LoginUserVO> userLoginWithPassword(@RequestBody UserLoginWithPasswordRequest userLoginWithPasswordRequest, HttpServletRequest request) {
        if (userLoginWithPasswordRequest == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "登录请求为空（账号密码登录）");
        }
        LoginUserVO loginUserVO = userService.userLoginWithPassword(userLoginWithPasswordRequest, request);
        return ResponseUtils.success(loginUserVO, "登录成功");
    }
    
    /**
     * 用户登录（使用邮箱验证码登录）
     */
    @PostMapping("/login/email")
    public BaseResponse<LoginUserVO> userLoginWithEmail(@RequestBody UserLoginWithEmailRequest userLoginWithEmailRequest, HttpServletRequest request) {
        if (userLoginWithEmailRequest == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "登录请求为空（邮箱验证码登录）");
        }
        LoginUserVO loginUserVO = userService.userLoginWithEmail(userLoginWithEmailRequest, request);
        return ResponseUtils.success(loginUserVO, "登录成功");
    }
    
    /**
     * 用户请求服务器发送邮箱验证码（登录使用）
     */
    @PostMapping("/login/request/email/code")
    public BaseResponse<UserRequestEmailCodeResponse> userLoginRequestEmailCode(@RequestBody(required = false) String email) {
        if (StringUtils.isBlank(email)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "邮箱地址为空");
        }
        UserRequestEmailCodeResponse result = userService.userLoginRequestEmailCode(email);
        return ResponseUtils.success(result, "验证码已发送，请注意查收");
    }
    
    /**
     * 用户注销
     */
    @PostMapping("/logout")
    @AuthCheck(mustRole = UserRoleEnum.BAN)
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "请求为空");
        }
        boolean result = userService.userLogout(request);
        return ResponseUtils.success(result, "注销成功");
    }
    
    /**
     * 获取当前登录用户（脱敏）
     */
    @GetMapping("/get/login")
    @AuthCheck(mustRole = UserRoleEnum.BAN)
    public BaseResponse<LoginUserVO> userGetLogin(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "请求为空");
        }
        LoginUserVO res = userService.userGetLogin(request);
        return ResponseUtils.success(res);
    }
}
