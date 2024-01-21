package asia.dyh1319.oj.model.vo;

import asia.dyh1319.oj.model.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * 用户视图（脱敏，该视图用于显示用户的公开信息）
 **/
@Data
public class UserVO implements Serializable {
    
    private static final long serialVersionUID = 2L;
    
    /**
     * 用户 id
     */
    private Long id;
    
    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;
    
    /**
     * 用户头像
     */
    private String userAvatar;
    
    /**
     * 用户昵称
     */
    private String username;
    
    public static UserVO objToVo(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
    
    public static User voToObj(UserVO userVO) {
        if (userVO == null) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        return user;
    }
}