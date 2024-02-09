package asia.dyh1319.oj.judge.strategy.impl;

import asia.dyh1319.oj.judge.strategy.JudgeContext;
import asia.dyh1319.oj.judge.strategy.JudgeStrategy;
import asia.dyh1319.oj.model.enums.JudgeInfoMessageEnum;

/**
 * C++判题策略
 */
public class CppJudgeStrategy implements JudgeStrategy {
    
    /**
     * 执行判题（对代码沙箱返回的结果进行判断）
     */
    @Override
    public JudgeInfoMessageEnum doJudge(JudgeContext judgeContext) {
        return JudgeInfoMessageEnum.SYSTEM_ERROR;
    }
}
