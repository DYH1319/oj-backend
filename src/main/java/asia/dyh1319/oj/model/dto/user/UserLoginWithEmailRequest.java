package asia.dyh1319.oj.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求（邮箱验证码登录）
 */
@Data
public class UserLoginWithEmailRequest implements Serializable {
    
    private static final long serialVersionUID = 3191241716373120793L;
    
    /**
     * 登录邮箱
     */
    private String email;
    
    /**
     * 邮箱验证码
     */
    private String emailVerificationCode;
}
