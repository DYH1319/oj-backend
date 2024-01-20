package asia.dyh1319.oj.utils;

import asia.dyh1319.oj.common.StatusCode;
import asia.dyh1319.oj.exception.BusinessException;

/**
 * 抛异常工具类
 */
public class ThrowUtils {

    /**
     * 条件成立则抛异常
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立则抛异常
     */
    public static void throwIf(boolean condition, StatusCode statusCode) {
        throwIf(condition, new BusinessException(statusCode));
    }

    /**
     * 条件成立则抛异常
     */
    public static void throwIf(boolean condition, StatusCode statusCode, String description) {
        throwIf(condition, new BusinessException(statusCode, description));
    }
}
