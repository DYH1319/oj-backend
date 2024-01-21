package asia.dyh1319.oj.model.dto.question;

import asia.dyh1319.oj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author DYH
 * @version 1.0
 * @since 2024/1/20 18:19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionQueryRequest extends PageRequest implements Serializable {
    
    private static final long serialVersionUID = -3504445122801833364L;
    
    /**
     * 主键，自动递增
     */
    private Long id;
    
    /**
     * 标题，唯一
     */
    private String title;
    
    /**
     * 内容，包含题目详细说明，输入输出格式等信息
     */
    private String content;
    
    /**
     * 难度（easy/normal/hard等）
     */
    private String questionLevel;
    
    /**
     * 标签列表（json数组）
     */
    private List<String> tags;
    
    /**
     * 标准答案
     */
    private String answer;
    
    /**
     * 创建用户id
     */
    private Long userId;
}
