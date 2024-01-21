package asia.dyh1319.oj.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author DYH
 * @version 1.0
 * @since 2024/1/20 17:40
 */
@Data
public class QuestionAddRequest implements Serializable {
    
    private static final long serialVersionUID = 3276959979949571742L;
    
    /**
     * 标题，唯一
     */
    private String title;
    
    /**
     * 内容，包含题目详细说明，输入输出格式等信息
     */
    private String content;
    
    /**
     * 标签列表（json数组）
     */
    private List<String> tags;
    
    /**
     * 标准答案
     */
    private String answer;
    
    /**
     * 判题配置（json对象，存储timeLimit、memoryLimit、stackLimit、判题类型judgeType等）
     */
    private JudgeConfig judgeConfig;
    
    /**
     * 判题用例（json数组，每一个元素中包含一对输入用例和输出用例）
     */
    private List<JudgeCase> judgeCases;
}
