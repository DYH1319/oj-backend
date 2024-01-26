package asia.dyh1319.oj.judge.codesandbox;

import asia.dyh1319.oj.judge.codesandbox.model.ExecuteCodeRequest;
import asia.dyh1319.oj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandbox {
    
    /**
     * 执行代码
     */
    ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest);
}
