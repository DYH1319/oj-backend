package asia.dyh1319.oj.judge.strategy;

import asia.dyh1319.oj.model.enums.JudgeInfoMessageEnum;

/**
 * 判题策略
 */
public interface JudgeStrategy {
    
    /**
     * 执行判题（对代码沙箱返回的结果进行判断）
     */
    JudgeInfoMessageEnum doJudge(JudgeContext judgeContext);
}
