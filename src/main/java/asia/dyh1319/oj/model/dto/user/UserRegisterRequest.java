package asia.dyh1319.oj.model.dto.user;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户注册请求体
 */
@Data
public class UserRegisterRequest implements Serializable {
    
    private static final long serialVersionUID = 3191241716373120793L;
    
    /**
     * 用户账号
     */
    private String userAccount;
    
    /**
     * 用户密码
     */
    private String userPassword;
    
    /**
     * 确认密码
     */
    private String checkPassword;
    
    /**
     * 注册邮箱
     */
    private String email;
    
    /**
     * 邮箱验证码
     */
    private String emailVerificationCode;
}
