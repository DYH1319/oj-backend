package asia.dyh1319.oj.model.dto.submit;

import lombok.Data;

import java.io.Serializable;

/**
 * @author DYH
 * @version 1.0
 * @since 2024/1/20 18:01
 */
@Data
public class JudgeInfo implements Serializable {
    
    private static final long serialVersionUID = -7149376623007815513L;
    
    /**
     * 程序执行信息
     */
    private String message;
    
    /**
     * 执行时间
     */
    private Long time;
    
    /**
     * 占用内存
     */
    private Long memory;
    
    /**
     * 代码长度
     */
    private String codeLength;
}
