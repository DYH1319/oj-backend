package asia.dyh1319.oj.exception;

import asia.dyh1319.oj.common.BaseResponse;
import asia.dyh1319.oj.common.StatusCode;
import asia.dyh1319.oj.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        // log.error("BusinessException: ", e);
        return ResponseUtils.fail(e.getStatusCode(), e.getDescription());
    }
    
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException: ", e);
        return ResponseUtils.fail(StatusCode.SYSTEM_ERROR);
    }
}
