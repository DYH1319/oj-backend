package asia.dyh1319.oj.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户请求服务器发送邮箱验证码响应体
 */
@Data
@AllArgsConstructor
public class UserRequestEmailCodeResponse implements Serializable {
    
    private static final long serialVersionUID = -3250475407412049581L;
    
    /**
     * 注册邮箱验证码过期时间
     */
    private Integer expireTime;
    
    /**
     * 注册邮箱验证码距离下一次可以请求的间隔时间
     */
    private Integer intervalTime;
}
