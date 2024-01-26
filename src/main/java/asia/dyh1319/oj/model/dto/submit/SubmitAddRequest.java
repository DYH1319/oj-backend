package asia.dyh1319.oj.model.dto.submit;

import lombok.Data;

import java.io.Serializable;

/**
 * @author DYH
 * @version 1.0
 * @since 2024/1/20 17:40
 */
@Data
public class SubmitAddRequest implements Serializable {
    
    private static final long serialVersionUID = -1175925910850972380L;
    
    /**
     * 编程语言
     */
    private String language;
    
    /**
     * 用户代码
     */
    private String code;
    
    /**
     * 题目id
     */
    private Long questionId;
}
