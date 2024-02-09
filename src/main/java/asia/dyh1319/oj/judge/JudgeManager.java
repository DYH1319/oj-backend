package asia.dyh1319.oj.judge;

import asia.dyh1319.oj.judge.strategy.JudgeContext;
import asia.dyh1319.oj.judge.strategy.JudgeStrategy;
import asia.dyh1319.oj.judge.strategy.impl.CppJudgeStrategy;
import asia.dyh1319.oj.judge.strategy.impl.DefaultJudgeStrategy;
import asia.dyh1319.oj.judge.strategy.impl.HtmlJudgeStrategy;
import asia.dyh1319.oj.model.enums.JudgeInfoMessageEnum;
import asia.dyh1319.oj.model.enums.SubmitLanguageEnum;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 */
@Service
public class JudgeManager {
    
    /**
     * 执行判题（对代码沙箱返回的结果进行判断）
     */
    public JudgeInfoMessageEnum doJudge(JudgeContext judgeContext) {
        SubmitLanguageEnum language = judgeContext.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        switch (language) {
            case HTML:
                judgeStrategy = new HtmlJudgeStrategy();
                break;
            case CPP:
                judgeStrategy = new CppJudgeStrategy();
                break;
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
