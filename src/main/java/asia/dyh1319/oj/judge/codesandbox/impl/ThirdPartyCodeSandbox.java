package asia.dyh1319.oj.judge.codesandbox.impl;

import asia.dyh1319.oj.judge.codesandbox.CodeSandbox;
import asia.dyh1319.oj.judge.codesandbox.model.ExecuteCodeRequest;
import asia.dyh1319.oj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 第三方代码沙箱（调用网上现成的代码沙箱）
 */
public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
