package asia.dyh1319.oj.judge.codesandbox.impl;

import asia.dyh1319.oj.judge.codesandbox.CodeSandbox;
import asia.dyh1319.oj.judge.codesandbox.model.ExecuteCodeRequest;
import asia.dyh1319.oj.judge.codesandbox.model.ExecuteCodeResponse;
import asia.dyh1319.oj.judge.codesandbox.model.JudgeInfo;
import asia.dyh1319.oj.model.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * 示例代码沙箱（仅为了跑通业务流程）
 */
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(0);
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
