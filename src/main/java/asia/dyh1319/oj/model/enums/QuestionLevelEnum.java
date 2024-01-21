package asia.dyh1319.oj.model.enums;

import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 题目难度枚举
 */
@Getter
public enum QuestionLevelEnum {
    
    EASY("简单", "Easy"),
    NORMAL("一般", "Normal"),
    HARD("困难", "Hard");
    
    private final String text;
    
    private final String value;
    
    QuestionLevelEnum(String text, String value) {
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
    public static QuestionLevelEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (QuestionLevelEnum anEnum : QuestionLevelEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
