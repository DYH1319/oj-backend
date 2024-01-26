package asia.dyh1319.oj.judge.codesandbox.impl;

import asia.dyh1319.oj.judge.codesandbox.CodeSandbox;
import asia.dyh1319.oj.judge.codesandbox.model.ExecuteCodeRequest;
import asia.dyh1319.oj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 远程代码沙箱（实际调用接口的沙箱）
 */
public class RemoteCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        return null;
    }
}
