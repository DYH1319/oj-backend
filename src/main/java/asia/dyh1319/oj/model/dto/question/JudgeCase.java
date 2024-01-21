package asia.dyh1319.oj.model.dto.question;

import lombok.Data;

import java.io.Serializable;

/**
 * @author DYH
 * @version 1.0
 * @className JudgeConfig
 * @since 2024/1/20 17:46
 */
@Data
public class JudgeCase implements Serializable {
    
    private static final long serialVersionUID = 8655149409693358064L;
    
    /**
     * 用例输入
     */
    private String input;
    
    /**
     * 用例输出
     */
    private String output;
}
