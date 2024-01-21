package asia.dyh1319.oj.controller;

import asia.dyh1319.oj.annotation.AuthCheck;
import asia.dyh1319.oj.common.BaseResponse;
import asia.dyh1319.oj.common.DeleteRequest;
import asia.dyh1319.oj.common.StatusCode;
import asia.dyh1319.oj.exception.BusinessException;
import asia.dyh1319.oj.model.dto.question.QuestionAddRequest;
import asia.dyh1319.oj.model.dto.question.QuestionQueryRequest;
import asia.dyh1319.oj.model.dto.question.QuestionUpdateRequest;
import asia.dyh1319.oj.model.entity.Question;
import asia.dyh1319.oj.model.enums.UserRoleEnum;
import asia.dyh1319.oj.model.vo.QuestionVO;
import asia.dyh1319.oj.service.QuestionService;
import asia.dyh1319.oj.utils.ResponseUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * question 控制器
 */
@RestController
@RequestMapping("/question")
public class QuestionController {
    
    @Resource
    private QuestionService questionService;
    
    /**
     * 新增题目
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserRoleEnum.ADMIN)
    public BaseResponse<Long> addQuestion(@RequestBody QuestionAddRequest questionAddRequest, HttpServletRequest request) {
        if (questionAddRequest == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "新建题目请求为空");
        }
        long res = questionService.addQuestion(questionAddRequest, request);
        return ResponseUtils.success(res, "添加题目成功");
    }
    
    /**
     * 删除题目
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserRoleEnum.ADMIN)
    public BaseResponse<Boolean> deleteQuestion(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "删除题目请求为空");
        }
        boolean res = questionService.deleteQuestion(deleteRequest, request);
        return ResponseUtils.success(res, "删除题目成功");
    }
    
    /**
     * 更新题目
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserRoleEnum.ADMIN)
    public BaseResponse<Boolean> updateQuestion(@RequestBody QuestionUpdateRequest questionUpdateRequest, HttpServletRequest request) {
        if (questionUpdateRequest == null || questionUpdateRequest.getId() == null || questionUpdateRequest.getId() <= 0) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "更新题目请求为空");
        }
        boolean res = questionService.updateQuestion(questionUpdateRequest, request);
        return ResponseUtils.success(res, "更新题目成功");
    }
    
    /**
     * 根据id获取题目（脱敏）
     */
    @GetMapping("/get/vo")
    @AuthCheck(mustRole = UserRoleEnum.USER)
    public BaseResponse<QuestionVO> getQuestionVOById(Long id, HttpServletRequest request) {
        if (id == null || id <= 0) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "请求id为空");
        }
        QuestionVO res = questionService.getQuestionVOById(id, request);
        return ResponseUtils.success(res, "获取题目成功");
    }
    
    /**
     * 根据id获取题目
     */
    @GetMapping("/get")
    @AuthCheck(mustRole = UserRoleEnum.ADMIN)
    public BaseResponse<Question> getQuestionById(Long id, HttpServletRequest request) {
        if (id == null || id <= 0) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "请求id为空");
        }
        Question res = questionService.getQuestionById(id, request);
        return ResponseUtils.success(res, "获取题目成功");
    }
    
    /**
     * 分页获取题目列表（脱敏）
     */
    @PostMapping("/list/page/vo")
    @AuthCheck(mustRole = UserRoleEnum.USER)
    public BaseResponse<Page<QuestionVO>> listQuestionVOByPage(@RequestBody QuestionQueryRequest questionQueryRequest, HttpServletRequest request) {
        if (questionQueryRequest == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "查询请求为空");
        }
        Page<QuestionVO> res = questionService.listQuestionVOByPage(questionQueryRequest, request);
        return ResponseUtils.success(res, "获取题目列表成功");
    }
    
    /**
     * 分页获取题目列表
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserRoleEnum.ADMIN)
    public BaseResponse<Page<Question>> listQuestionByPage(@RequestBody QuestionQueryRequest questionQueryRequest, HttpServletRequest request) {
        if (questionQueryRequest == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "查询请求为空");
        }
        Page<Question> res = questionService.listQuestionByPage(questionQueryRequest, request);
        return ResponseUtils.success(res, "获取题目列表成功");
    }
    
}
