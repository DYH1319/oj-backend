package asia.dyh1319.oj.model.vo;

import asia.dyh1319.oj.model.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 已登录用户视图（脱敏，该视图用于显示用户自己的信息，只有自己才能看到）
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
    
    public static LoginUserVO objToVo(User loginUser) {
        if (loginUser == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(loginUser, loginUserVO);
        return loginUserVO;
    }
    
    public static User voToObj(LoginUserVO loginUserVO) {
        if (loginUserVO == null) {
            return null;
        }
        User loginUser = new User();
        BeanUtils.copyProperties(loginUserVO, loginUser);
        return loginUser;
    }
}