package asia.dyh1319.oj.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 已登录用户视图（脱敏）
 **/
@Data
public class LoginUserVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户 id
     */
    private Long id;
    
    /**
     * 用户账号
     */
    private String userAccount;
    
    /**
     * 用户角色：user/admin/ban
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
     * 邮箱地址
     */
    private String email;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
}