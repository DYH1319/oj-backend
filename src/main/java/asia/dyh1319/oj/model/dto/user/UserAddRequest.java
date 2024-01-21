package asia.dyh1319.oj.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author DYH
 * @version 1.0
 * @since 2024/1/20 17:40
 */
@Data
public class UserAddRequest implements Serializable {
    
    private static final long serialVersionUID = -200726641846644804L;
    
    /**
     * 用户账号，唯一
     */
    private String userAccount;
    
    /**
     * 用户密码，使用SHA256加密
     */
    private String userPassword;
    
    /**
     * 用户身份：ban/user/admin
     */
    private String userRole;
    
    /**
     * 用户头像
     */
    private String userAvatar;
    
    /**
     * 用户简介
     */
    private String userProfile;
    
    /**
     * 用户昵称
     */
    private String username;
    
    /**
     * 用户邮箱，唯一
     */
    private String email;
    
    /**
     * 用户手机号，唯一
     */
    private String phone;
}
