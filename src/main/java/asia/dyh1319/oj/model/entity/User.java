package asia.dyh1319.oj.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_user")
public class User {
    private static final long serialVersionUID = 2728240965754933253L;
    /**
     * 主键，自动递增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 用户账号，唯一
     */
    @TableField(value = "user_account")
    private String userAccount;
    
    /**
     * 用户密码，使用SHA256加密
     */
    @TableField(value = "user_password")
    private String userPassword;
    
    /**
     * 用户身份：ban/user/admin
     */
    @TableField(value = "user_role")
    private String userRole;
    
    /**
     * 用户头像
     */
    @TableField(value = "user_avatar")
    private String userAvatar;
    
    /**
     * 用户简介
     */
    @TableField(value = "user_profile")
    private String userProfile;
    
    /**
     * 用户昵称
     */
    @TableField(value = "username")
    private String username;
    
    /**
     * 用户邮箱，唯一
     */
    @TableField(value = "email")
    private String email;
    
    /**
     * 用户手机号，唯一
     */
    @TableField(value = "phone")
    private String phone;
    
    /**
     * 是否逻辑删除
     */
    @TableLogic
    @TableField(value = "is_deleted")
    private Byte isDeleted;
    
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;
    
    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private Date updateTime;
}