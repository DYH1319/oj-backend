package asia.dyh1319.oj.judge.codesandbox.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author DYH
 * @version 1.0
 * @className JudgeConfig
 * @since 2024/1/20 17:46
 */
@Data
public class JudgeConfig implements Serializable {
    
    private static final long serialVersionUID = 1499902718172197480L;
    
    /**
     * 时间限制（单位：MS）
     */
    private Long timeLimit;
    
    /**
     * 内存限制（单位：KB）
     */
    private Long memoryLimit;
    
    /**
     * 堆栈限制（单位：KB）
     */
    private Long stackLimit;
    
    /**
     * 判题类型
     */
    private String judgeType;
}
