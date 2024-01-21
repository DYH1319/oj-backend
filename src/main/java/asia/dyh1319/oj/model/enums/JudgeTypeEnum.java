package asia.dyh1319.oj.model.enums;

import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 判题类型枚举
 */
@Getter
public enum JudgeTypeEnum {

    REGULAR("普通测评", "Regular"),
    SPECIAL("特殊测评", "Special"),
    INTERACTIVE("交互测评", "Interactive"),
    SELF("在线自测", "Self"),
    SUBTASK("子任务分组评测", "Subtask"),
    FILE("文件IO", "File");
    
    private final String text;
    
    private final String value;
    
    JudgeTypeEnum(String text, String value) {
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
    public static JudgeTypeEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (JudgeTypeEnum anEnum : JudgeTypeEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
