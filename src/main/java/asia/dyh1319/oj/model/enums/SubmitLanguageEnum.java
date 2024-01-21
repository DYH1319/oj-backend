package asia.dyh1319.oj.model.enums;

import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 提交编程语言枚举
 */
@Getter
public enum SubmitLanguageEnum {
    
    JAVA("Java", "Java"),
    CPP("C++", "C++"),
    PYTHON("Python", "Python");
    
    private final String text;
    
    private final String value;
    
    SubmitLanguageEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }
    
    /**
     * 获取值列表
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }
    
    /**
     * 根据 value 获取枚举
     */
    public static SubmitLanguageEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (SubmitLanguageEnum anEnum : SubmitLanguageEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
