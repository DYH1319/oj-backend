package asia.dyh1319.oj.judge.strategy.impl;

import asia.dyh1319.oj.judge.strategy.JudgeContext;
import asia.dyh1319.oj.judge.strategy.JudgeStrategy;
import asia.dyh1319.oj.model.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * 默认判题策略
 */
public class DefaultJudgeStrategy implements JudgeStrategy {
    
    /**
     * 执行判题（对代码沙箱返回的结果进行判断）
     */
    @Override
    public JudgeInfoMessageEnum doJudge(JudgeContext judgeContext) {
        long time = judgeContext.getTime();
        long memory = judgeContext.getMemory();
        long timeLimit = judgeContext.getTimeLimit();
        long memoryLimit = judgeContext.getMemoryLimit();
        List<String> outputList = judgeContext.getOutputList();
        List<String> standardOutputList = judgeContext.getStandardOutputList();
        
        // 判题
        // 1. 若执行时间大于时间限制
        if (time > timeLimit) {
            return JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
        }
        // 2. 若占用内存大于内存限制
        if (memory > memoryLimit) {
            return JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
        }
        // 3. 若程序输出与预期输出的个数不同
        if (outputList.size() != standardOutputList.size()) {
            return JudgeInfoMessageEnum.WRONG_ANSWER;
        }
        // 4. 依次判断每一项输出和预期输出是否相等
        for (int i = 0; i < standardOutputList.size(); i++) {
            if (!standardOutputList.get(i).equals(outputList.get(i))) {
                return JudgeInfoMessageEnum.WRONG_ANSWER;
            }
        }
        return JudgeInfoMessageEnum.ACCEPTED;
    }
}
