package asia.dyh1319.oj.model.dto.user;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户登录请求（账号密码登录）
 */
@Data
public class UserLoginWithPasswordRequest implements Serializable {
    
    private static final long serialVersionUID = 3191241716373120793L;
    
    private String userAccount;
    
    private String userPassword;
}
