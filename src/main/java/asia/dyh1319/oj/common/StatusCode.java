package asia.dyh1319.oj.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自定义错误码
 */
@Getter
@AllArgsConstructor
public enum StatusCode {
    
    SUCCESS(20000, "OK", "成功"),
    PARAMS_ERROR(40000, "请求参数错误", ""),
    NULL_ERROR(40001, "响应数据为空", ""),
    NOT_LOGIN_ERROR(40100, "未登录", ""),
    NO_AUTH_ERROR(40101, "无权限", ""),
    NOT_FOUND_ERROR(40400, "请求数据不存在", ""),
    FORBIDDEN_ERROR(40300, "禁止访问", ""),
    SYSTEM_ERROR(50000, "系统内部异常", ""),
    OPERATION_ERROR(50001, "操作失败", "");
    
    /**
     * 状态码
     */
    private final int code;
    
    /**
     * 状态码信息（简述）
     */
    private final String message;
    
    /**
     * 状态码描述（详情）
     */
    private final String description;
}
