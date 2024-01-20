package asia.dyh1319.oj.utils;

import asia.dyh1319.oj.common.BaseResponse;
import asia.dyh1319.oj.common.StatusCode;

/**
 * 返回工具类
 */
public class ResponseUtils {
    
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(StatusCode.SUCCESS, data);
    }
    
    public static <T> BaseResponse<T> success(T data, String description) {
        return new BaseResponse<>(StatusCode.SUCCESS, data, description);
    }
    
    public static <T> BaseResponse<T> fail(StatusCode errorCode) {
        return new BaseResponse<>(errorCode);
    }
    
    public static <T> BaseResponse<T> fail(StatusCode errorCode, String description) {
        return new BaseResponse<>(errorCode, description);
    }
}
