package asia.dyh1319.oj.service.impl;

import asia.dyh1319.oj.common.StatusCode;
import asia.dyh1319.oj.constant.UserConstant;
import asia.dyh1319.oj.exception.BusinessException;
import asia.dyh1319.oj.judge.JudgeService;
import asia.dyh1319.oj.mapper.QuestionMapper;
import asia.dyh1319.oj.mapper.SubmitMapper;
import asia.dyh1319.oj.model.dto.submit.SubmitAddRequest;
import asia.dyh1319.oj.model.entity.Question;
import asia.dyh1319.oj.model.entity.Submit;
import asia.dyh1319.oj.model.entity.User;
import asia.dyh1319.oj.model.enums.SubmitLanguageEnum;
import asia.dyh1319.oj.model.enums.SubmitStatusEnum;
import asia.dyh1319.oj.service.SubmitService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author DYH
 * @version 1.0
 * @since 2024/1/20 16:33
 */

@Service
public class SubmitServiceImpl extends ServiceImpl<SubmitMapper, Submit> implements SubmitService {
    
    @Resource
    private QuestionMapper questionMapper;
    
    @Resource
    private SubmitMapper submitMapper;
    
    @Resource
    private JudgeService judgeService;
    
    @Override
    public long addSubmit(SubmitAddRequest submitAddRequest, HttpServletRequest request) {
        String language = submitAddRequest.getLanguage();
        String code = submitAddRequest.getCode();
        Long questionId = submitAddRequest.getQuestionId();
        // 校验
        if (StringUtils.isBlank(code)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "提交的代码不能为空");
        }
        if (SubmitLanguageEnum.getEnumByCodeEditorValue(language) == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "不支持此语言");
        }
        if (questionMapper.selectOne(new LambdaQueryWrapper<Question>().eq(Question::getId, questionId)) == null) {
            throw new BusinessException(StatusCode.NOT_FOUND_ERROR, "提交所对应的题目不存在");
        }
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (user == null) {
            throw new BusinessException(StatusCode.NOT_LOGIN_ERROR);
        }
        Submit submit = new Submit();
        // noinspection DataFlowIssue
        submit.setSubmitLanguage(SubmitLanguageEnum.getEnumByCodeEditorValue(language).getValue());
        submit.setCode(code);
        // submit.setJudgeInfo("{}");
        submit.setSubmitStatus(SubmitStatusEnum.WAITING.getValue());
        submit.setQuestionId(questionId);
        submit.setUserId(user.getId());
        int count = submitMapper.insert(submit);
        if (count != 1) {
            throw new BusinessException(StatusCode.SYSTEM_ERROR, "无法提交");
        }
        Long submitId = submit.getId();
        // 异步执行判题服务
        CompletableFuture.runAsync(() -> judgeService.doJudge(submitId));
        return submitId;
    }
    
    @Override
    public List<List<String>> fetchLanguages() {
        List<List<String>> lists = new ArrayList<>();
        lists.add(SubmitLanguageEnum.getValues());
        lists.add(SubmitLanguageEnum.getCodeEditorValues());
        return lists;
    }
}
