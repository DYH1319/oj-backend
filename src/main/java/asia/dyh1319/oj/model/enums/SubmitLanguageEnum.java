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
    
    JAVA("Java", "Java", "java"),
    CPP("C++", "C++", "cpp"),
    PYTHON("Python", "Python", "python"),
    HTML("Html", "Html", "html");
    
    private final String text;
    
    private final String value;
    
    /**
     * 前端codeEditor中对应的值
     */
    private final String codeEditorValue;
    
    SubmitLanguageEnum(String text, String value, String codeEditorValue) {
        this.text = text;
        this.value = value;
        this.codeEditorValue = codeEditorValue;
    }
    
    /**
     * 获取值列表
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }
    
    /**
     * 获取codeEditor值列表
     */
    public static List<String> getCodeEditorValues() {
        return Arrays.stream(values()).map(item -> item.codeEditorValue).collect(Collectors.toList());
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
    
    /**
     * 根据 codeEditorValue 获取枚举
     */
    public static SubmitLanguageEnum getEnumByCodeEditorValue(String codeEditorValue) {
        if (ObjectUtils.isEmpty(codeEditorValue)) {
            return null;
        }
        for (SubmitLanguageEnum anEnum : SubmitLanguageEnum.values()) {
            if (anEnum.codeEditorValue.equals(codeEditorValue)) {
                return anEnum;
            }
        }
        return null;
    }
}
