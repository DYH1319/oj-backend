package asia.dyh1319.oj.judge;

import asia.dyh1319.oj.common.StatusCode;
import asia.dyh1319.oj.exception.BusinessException;
import asia.dyh1319.oj.judge.codesandbox.CodeSandbox;
import asia.dyh1319.oj.judge.codesandbox.CodeSandboxFactory;
import asia.dyh1319.oj.judge.codesandbox.CodeSandboxProxy;
import asia.dyh1319.oj.judge.codesandbox.model.ExecuteCodeRequest;
import asia.dyh1319.oj.judge.codesandbox.model.ExecuteCodeResponse;
import asia.dyh1319.oj.judge.codesandbox.model.JudgeInfo;
import asia.dyh1319.oj.judge.strategy.JudgeContext;
import asia.dyh1319.oj.mapper.QuestionMapper;
import asia.dyh1319.oj.mapper.SubmitMapper;
import asia.dyh1319.oj.model.dto.question.JudgeCase;
import asia.dyh1319.oj.model.dto.question.JudgeConfig;
import asia.dyh1319.oj.model.entity.Question;
import asia.dyh1319.oj.model.entity.Submit;
import asia.dyh1319.oj.model.enums.JudgeInfoMessageEnum;
import asia.dyh1319.oj.model.enums.SubmitLanguageEnum;
import asia.dyh1319.oj.model.enums.SubmitStatusEnum;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author DYH
 * @version 1.0
 * @since 2024/1/25 20:52
 */
@Service
public class JudgeServiceImpl implements JudgeService {
    
    @Resource
    private SubmitMapper submitMapper;
    
    @Resource
    private QuestionMapper questionMapper;
    
    @Resource
    private JudgeManager judgeManager;
    
    /**
     * 从springboot配置文件中获取配置（若不存在该配置，设置其默认值为example）
     */
    @Value("${code-sandbox.type:example}")
    private String codeSandboxType;
    
    @Override
    public void doJudge(Long submitId) {
        // 1. 获取提交和题目对象
        if (submitId == null || submitId <= 0) {
            throw new BusinessException(StatusCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        Submit submit = submitMapper.selectById(submitId);
        if (submit == null) {
            throw new BusinessException(StatusCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        Question question = questionMapper.selectById(submit.getQuestionId());
        if (question == null) {
            throw new BusinessException(StatusCode.NOT_FOUND_ERROR, "提交对应的题目不存在");
        }
        // 2. 若提交状态不为等待中，则拒绝判题，避免重复执行判题
        if (!SubmitStatusEnum.WAITING.getValue().equals(submit.getSubmitStatus())) {
            throw new BusinessException(StatusCode.OPERATION_ERROR, "提交正在判题中或已经完成判题");
        }
        // 3. 设置提交状态为判题中
        Submit updateSubmitStatus = new Submit();
        updateSubmitStatus.setId(submitId);
        updateSubmitStatus.setSubmitStatus(SubmitStatusEnum.JUDGING.getValue());
        if (submitMapper.updateById(updateSubmitStatus) != 1) {
            throw new BusinessException(StatusCode.SYSTEM_ERROR, "无法更新提交状态");
        }
        // 4. 调用沙箱执行代码，并获得代码执行结果
        JudgeConfig judgeConfig = JSONUtil.toBean(question.getJudgeConfig(), JudgeConfig.class);
        List<JudgeCase> judgeCases = JSONUtil.toList(question.getJudgeCases(), JudgeCase.class);
        CodeSandbox codeSandbox = new CodeSandboxProxy(CodeSandboxFactory.newInstance(codeSandboxType));
        ExecuteCodeResponse executeCodeResponse =
            codeSandbox.execute(ExecuteCodeRequest.builder()
                .code(submit.getCode())
                .language(submit.getSubmitLanguage())
                .judgeConfig(judgeConfig)
                .inputList(judgeCases.stream().map(JudgeCase::getInput).collect(Collectors.toList()))
                .build());
        // 5. 根据沙箱的执行结果，设置题目的判题状态和信息
        Submit submitUpdate = new Submit();
        submitUpdate.setId(submitId);
        if (executeCodeResponse.getStatus() == 0) { // 执行成功
            JudgeInfo judgeInfo = executeCodeResponse.getJudgeInfo();
            submitUpdate.setSubmitStatus(SubmitStatusEnum.SUCCEED.getValue());
            if (judgeInfo.getMessage() == null) { // 若沙箱没有返回题目执行信息，代表需要进一步判断
                JudgeInfoMessageEnum judgeInfoMessageEnum =
                    judgeManager.doJudge(JudgeContext.builder()
                        .time(judgeInfo.getTime())
                        .memory(judgeInfo.getMemory())
                        .timeLimit(judgeConfig.getTimeLimit())
                        .memoryLimit(judgeConfig.getMemoryLimit())
                        .outputList(executeCodeResponse.getOutputList())
                        .standardOutputList(judgeCases.stream().map(JudgeCase::getOutput).collect(Collectors.toList()))
                        .language(SubmitLanguageEnum.getEnumByValue(submit.getSubmitLanguage()))
                        .build());
                judgeInfo.setMessage(judgeInfoMessageEnum.getValue());
            }
            submitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        } else { // 执行失败（沙箱内部的问题，导致判题无法完成）
            submitUpdate.setSubmitStatus(SubmitStatusEnum.FAILED.getValue());
            submitUpdate.setJudgeInfo(JSONUtil.toJsonStr(JudgeInfo.builder().message(JudgeInfoMessageEnum.SYSTEM_ERROR.getValue()).build()));
        }
        // 6. 修改数据库中的判题结果
        if (submitMapper.updateById(submitUpdate) != 1) {
            throw new BusinessException(StatusCode.SYSTEM_ERROR, "无法将判题结果更新至数据库");
        }
    }
}
