package asia.dyh1319.oj.service.impl;

import asia.dyh1319.oj.common.DeleteRequest;
import asia.dyh1319.oj.common.StatusCode;
import asia.dyh1319.oj.constant.CommonConstant;
import asia.dyh1319.oj.constant.UserConstant;
import asia.dyh1319.oj.exception.BusinessException;
import asia.dyh1319.oj.mapper.QuestionMapper;
import asia.dyh1319.oj.mapper.UserMapper;
import asia.dyh1319.oj.model.dto.question.*;
import asia.dyh1319.oj.model.entity.Question;
import asia.dyh1319.oj.model.entity.User;
import asia.dyh1319.oj.model.enums.JudgeTypeEnum;
import asia.dyh1319.oj.model.vo.QuestionVO;
import asia.dyh1319.oj.model.vo.UserVO;
import asia.dyh1319.oj.service.QuestionService;
import asia.dyh1319.oj.utils.SqlUtils;
import asia.dyh1319.oj.utils.ThrowUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author DYH
 * @version 1.0
 * @since 2024/1/20 16:35
 */

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {
    
    private static final Gson GSON = new Gson();
    
    @Resource
    private QuestionMapper questionMapper;
    
    @Resource
    private UserMapper userMapper;
    
    @Override
    public long addQuestion(QuestionAddRequest questionAddRequest, HttpServletRequest request) {
        Question question = new Question();
        BeanUtils.copyProperties(questionAddRequest, question);
        List<String> tags = questionAddRequest.getTags();
        List<JudgeCase> judgeCases = questionAddRequest.getJudgeCases();
        JudgeConfig judgeConfig = questionAddRequest.getJudgeConfig();
        // 校验
        validQuestion(question, tags, judgeCases, judgeConfig);
        // 标题唯一性校验
        if (questionMapper.selectOne(new LambdaQueryWrapper<Question>().eq(Question::getTitle, question.getTitle())) != null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "题目标题已存在");
        }
        if (tags != null) {
            question.setTags(GSON.toJson(tags));
        }
        question.setJudgeCases(GSON.toJson(judgeCases));
        question.setJudgeConfig(GSON.toJson(judgeConfig));
        question.setUserId(((User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE)).getId());
        int count = questionMapper.insert(question);
        if (count != 1) {
            throw new BusinessException(StatusCode.SYSTEM_ERROR, "系统内部异常，新增题目失败");
        }
        return question.getId();
    }
    
    @Override
    public boolean deleteQuestion(DeleteRequest deleteRequest, HttpServletRequest request) {
        long id = deleteRequest.getId();
        if (questionMapper.selectOne(new LambdaQueryWrapper<Question>().eq(Question::getId, id)) == null) {
            throw new BusinessException(StatusCode.NOT_FOUND_ERROR, "未找到需要删除的题目");
        }
        int count = questionMapper.deleteById(id);
        if (count != 1) {
            throw new BusinessException(StatusCode.SYSTEM_ERROR, "系统内部异常，删除题目失败");
        }
        return false;
    }
    
    @Override
    public boolean updateQuestion(QuestionUpdateRequest questionUpdateRequest, HttpServletRequest request) {
        Question question = new Question();
        BeanUtils.copyProperties(questionUpdateRequest, question);
        List<String> tags = questionUpdateRequest.getTags();
        List<JudgeCase> judgeCases = questionUpdateRequest.getJudgeCases();
        JudgeConfig judgeConfig = questionUpdateRequest.getJudgeConfig();
        // 校验
        validQuestion(question, tags, judgeCases, judgeConfig);
        if (tags != null) {
            question.setTags(GSON.toJson(tags));
        }
        question.setJudgeCases(GSON.toJson(judgeCases));
        question.setJudgeConfig(GSON.toJson(judgeConfig));
        long id = questionUpdateRequest.getId();
        if (questionMapper.selectOne(new LambdaQueryWrapper<Question>().eq(Question::getId, id)) == null) {
            throw new BusinessException(StatusCode.NOT_FOUND_ERROR, "未找到需要更新的题目");
        }
        question.setId(id);
        // 标题唯一性校验
        if (questionMapper.selectOne(new LambdaQueryWrapper<Question>().eq(Question::getTitle, question.getTitle()).ne(Question::getId, id)) != null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "题目标题已存在");
        }
        int count = questionMapper.updateById(question);
        if (count != 1) {
            throw new BusinessException(StatusCode.SYSTEM_ERROR, "系统内部异常，更新题目失败");
        }
        return true;
    }
    
    @Override
    public QuestionVO getQuestionVOById(Long id, HttpServletRequest request) {
        Question question = questionMapper.selectOne(new LambdaQueryWrapper<Question>().eq(Question::getId, id));
        if (question == null) {
            throw new BusinessException(StatusCode.NOT_FOUND_ERROR, "查询的题目不存在");
        }
        QuestionVO questionVO = QuestionVO.objToVo(question);
        // 关联创建用户信息
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getId, question.getUserId()));
        questionVO.setUserVO(UserVO.objToVo(user));
        return questionVO;
    }
    
    @Override
    public Question getQuestionById(Long id, HttpServletRequest request) {
        Question question = questionMapper.selectOne(new LambdaQueryWrapper<Question>().eq(Question::getId, id));
        if (question == null) {
            throw new BusinessException(StatusCode.NOT_FOUND_ERROR, "查询的题目不存在");
        }
        return question;
    }
    
    @Override
    public Page<QuestionVO> listQuestionVOByPage(QuestionQueryRequest questionQueryRequest, HttpServletRequest request) {
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 10, StatusCode.OPERATION_ERROR, "禁止同时请求多页数据");
        Page<Question> questionPage = questionMapper.selectPage(new Page<>(current, size), getLambdaQueryWrapper(questionQueryRequest));
        // Page<Question> -> Page<QuestionVO>
        List<Question> questionList = questionPage.getRecords();
        Page<QuestionVO> questionVOPage = new Page<>(questionPage.getCurrent(), questionPage.getSize(), questionPage.getTotal());
        if (CollectionUtils.isEmpty(questionList)) {
            return questionVOPage;
        }
        // 关联创建用户信息
        Set<Long> userIdSet = questionList.stream().map(Question::getUserId).collect(Collectors.toSet());
        Map<Long, User> userIdUserListMap = userMapper.selectBatchIds(userIdSet).stream().collect(Collectors.toMap(User::getId, user -> user));
        // 填充信息
        List<QuestionVO> questionVOList = questionList.stream().map(question -> {
            QuestionVO questionVO = QuestionVO.objToVo(question);
            Long userId = question.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId);
            }
            questionVO.setUserVO(UserVO.objToVo(user));
            return questionVO;
        }).collect(Collectors.toList());
        questionVOPage.setRecords(questionVOList);
        return questionVOPage;
    }
    
    @Override
    public Page<Question> listQuestionByPage(QuestionQueryRequest questionQueryRequest, HttpServletRequest request) {
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 50, StatusCode.OPERATION_ERROR, "禁止同时请求多页数据");
        return questionMapper.selectPage(new Page<>(current, size), getLambdaQueryWrapper(questionQueryRequest));
    }
    
    /**
     * 校验题目是否合法
     * @param question 待校验的题目
     */
    private void validQuestion(Question question, List<String> tags, List<JudgeCase> judgeCases, JudgeConfig judgeConfig) {
        if (question == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "待校验的题目为空");
        }
        String title = question.getTitle();
        String content = question.getContent();
        String answer = question.getAnswer();
        // 校验字段
        if (StringUtils.isBlank(title)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "题目标题不能为空");
        }
        if (title.length() > 512) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "题目标题过长");
        }
        if (StringUtils.isBlank(content)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "题目内容不能为空");
        }
        if (content.length() > 65536) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "题目内容过长");
        }
        if (StringUtils.isNotBlank(answer) && answer.length() > 65536) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "题目答案过长");
        }
        if (ObjectUtils.isNotEmpty(tags)) {
            if (tags.size() > 100) {
                throw new BusinessException(StatusCode.PARAMS_ERROR, "题目标签个数过多");
            }
            tags.forEach(tag -> {
                if (tag.length() > 10) {
                    throw new BusinessException(StatusCode.PARAMS_ERROR, "标签'" + tag + "'字符数过多");
                }
            });
        }
        if (ObjectUtils.isEmpty(judgeCases)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "题目判题用例不能为空");
        }
        if (judgeCases.size() > 1000) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "题目判题用例过多，请使用文件存储判题用例");
        }
        judgeCases.forEach(judgeCase -> {
            if (StringUtils.isAnyBlank(judgeCase.getInput(), judgeCase.getOutput())) {
                throw new BusinessException(StatusCode.PARAMS_ERROR, "题目判题用例输入输出内容不能为空");
            }
        });
        if (ObjectUtils.isEmpty(judgeConfig)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "题目判题配置不能为空");
        }
        if (judgeConfig.getTimeLimit() == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "题目判题配置中的时间限制不能为空");
        }
        if (judgeConfig.getMemoryLimit() == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "题目判题配置中的内存限制不能为空");
        }
        if (JudgeTypeEnum.getEnumByValue(judgeConfig.getJudgeType()) == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "题目判题配置中的判题类型不存在");
        }
    }
    
    /**
     * 获取lambda查询包装类（封装查询条件）
     */
    private LambdaQueryWrapper<Question> getLambdaQueryWrapper(QuestionQueryRequest questionQueryRequest) {
        LambdaQueryWrapper<Question> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (questionQueryRequest == null) {
            return lambdaQueryWrapper;
        }
        Long id = questionQueryRequest.getId();
        String title = questionQueryRequest.getTitle();
        String content = questionQueryRequest.getContent();
        String questionLevel = questionQueryRequest.getQuestionLevel();
        List<String> tags = questionQueryRequest.getTags();
        String answer = questionQueryRequest.getAnswer();
        Long userId = questionQueryRequest.getUserId();
        String sortField = questionQueryRequest.getSortField();
        String sortOrder = questionQueryRequest.getSortOrder();
        
        // 拼接查询条件
        lambdaQueryWrapper.like(StringUtils.isNotBlank(title), Question::getTitle, title);
        lambdaQueryWrapper.like(StringUtils.isNotBlank(content), Question::getContent, content);
        lambdaQueryWrapper.like(StringUtils.isNotBlank(answer), Question::getAnswer, answer);
        lambdaQueryWrapper.like(StringUtils.isNotBlank(questionLevel), Question::getQuestionLevel, questionLevel);
        if (CollectionUtils.isNotEmpty(tags)) {
            for (String tag : tags) {
                lambdaQueryWrapper.like(Question::getTags, "\"" + tag + "\"");
            }
        }
        lambdaQueryWrapper.eq(ObjectUtils.isNotEmpty(id), Question::getId, id);
        lambdaQueryWrapper.eq(ObjectUtils.isNotEmpty(userId), Question::getUserId, userId);
        if (StringUtils.isNotBlank(sortField)) {
            switch (sortField) {
                case "id":
                    lambdaQueryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), Question::getId);
                    break;
                case "title":
                    lambdaQueryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), Question::getTitle);
                    break;
                case "content":
                    lambdaQueryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), Question::getContent);
                    break;
                case "questionLevel":
                    lambdaQueryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), Question::getQuestionLevel);
                    break;
                case "answer":
                    lambdaQueryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), Question::getAnswer);
                    break;
                case "userId":
                    lambdaQueryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), Question::getUserId);
                    break;
            }
        }
        return lambdaQueryWrapper;
    }
}
