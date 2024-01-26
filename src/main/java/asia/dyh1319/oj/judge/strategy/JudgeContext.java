package asia.dyh1319.oj.judge.strategy;

import asia.dyh1319.oj.model.enums.SubmitLanguageEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 判题上下文（用于定义在策略中传递的参数）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JudgeContext {
    
    private long time;
    private long memory;
    private long timeLimit;
    private long memoryLimit;
    private List<String> outputList;
    private List<String> standardOutputList;
    private SubmitLanguageEnum language;
}
