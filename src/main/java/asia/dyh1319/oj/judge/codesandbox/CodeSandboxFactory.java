package asia.dyh1319.oj.judge.codesandbox;

import asia.dyh1319.oj.judge.codesandbox.impl.ExampleCodeSandbox;
import asia.dyh1319.oj.judge.codesandbox.impl.RemoteCodeSandbox;
import asia.dyh1319.oj.judge.codesandbox.impl.ThirdPartyCodeSandbox;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

/**
 * 代码沙箱工厂（根据字符串参数创建指定的代码沙箱实例）
 */
public class CodeSandboxFactory {
    
    /**
     * 创建代码沙箱实例
     *
     * @param type 要创建的代码沙箱类型
     * @return 根据类型创建的沙箱实例，若没有对应类型的沙箱实例，返回null
     */
    public static @Nullable CodeSandbox newInstance(@NotNull String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return null;
        }
    }
}
