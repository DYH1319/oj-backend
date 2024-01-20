package asia.dyh1319.oj.constant;

/**
 * 用户常量
 */
public interface UserConstant {
    
    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "userLoginState";
    
    /**
     * 盐值，混淆密码
     */
    String SALT = "dyh1319";
    
    // region 权限
    
    /**
     * 普通用户角色
     */
    String USER_ROLE = "user";
    
    /**
     * 管理员角色
     */
    String ADMIN_ROLE = "admin";
    
    /**
     * 被封号
     */
    String BAN_ROLE = "ban";
    
    // endregion
}
