package asia.dyh1319.oj.judge.codesandbox.impl;

import asia.dyh1319.oj.common.StatusCode;
import asia.dyh1319.oj.exception.BusinessException;
import asia.dyh1319.oj.judge.codesandbox.CodeSandbox;
import asia.dyh1319.oj.judge.codesandbox.model.ExecuteCodeRequest;
import asia.dyh1319.oj.judge.codesandbox.model.ExecuteCodeResponse;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

/**
 * 远程代码沙箱（实际调用接口的沙箱）
 */
public class RemoteCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest) {
        String url = "http://192.168.84.134:9090/executeCode";
        String responseBody = HttpUtil.createPost(url)
            .body(JSONUtil.toJsonStr(executeCodeRequest))
            .execute()
            .body();
        if (StrUtil.isBlank(responseBody)) {
            throw new BusinessException(StatusCode.SYSTEM_ERROR, "远程判题沙箱异常" + responseBody);
        }
        return JSONUtil.toBean(responseBody, ExecuteCodeResponse.class);
    }
}
