package asia.dyh1319.oj.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

/**
 * 用户角色枚举
 */
@Getter
public enum UserRoleEnum {
    
    NOT_LOGIN("未登录", "notLogin", 0),
    BAN("被封号", "ban", 1),
    USER("用户", "user", 2),
    ADMIN("管理员", "admin", 3);
    
    private final String text;
    
    private final String value;
    
    private final int weight;
    
    UserRoleEnum(String text, String value, int weight) {
        this.text = text;
        this.value = value;
        this.weight = weight;
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
    public static UserRoleEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (UserRoleEnum anEnum : UserRoleEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
    
    /**
     * 根据 value 获取权限权重
     */
    public static int getWeightByValue(String value) {
        UserRoleEnum userRoleEnum = getEnumByValue(value);
        if (userRoleEnum == null) {
            return -1;
        }
        return userRoleEnum.getWeight();
    }
}
