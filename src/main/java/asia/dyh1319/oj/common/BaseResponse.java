package asia.dyh1319.oj.common;

import java.io.Serializable;

import lombok.Data;

/**
 * 通用返回类
 *
 * @param <T> 返回的类型
 */
@Data
public class BaseResponse<T> implements Serializable {
    private int code;
    private T data;
    private String message;
    private String description;
    
    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }
    
    public BaseResponse(int code, T data) {
        this(code, data, "", "");
    }
    
    public BaseResponse(StatusCode statusCode, T data) {
        this(statusCode.getCode(), data, statusCode.getMessage(), statusCode.getDescription());
    }
    
    public BaseResponse(StatusCode statusCode, T data, String description) {
        this(statusCode.getCode(), data, statusCode.getMessage(), description);
    }
    
    public BaseResponse(StatusCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }
    
    public BaseResponse(StatusCode errorCode, String errorDescription) {
        this(errorCode.getCode(), null, errorCode.getMessage(), errorDescription);
    }
}
