package asia.dyh1319.oj.service;

import asia.dyh1319.oj.common.DeleteRequest;
import asia.dyh1319.oj.model.dto.question.QuestionAddRequest;
import asia.dyh1319.oj.model.dto.question.QuestionQueryRequest;
import asia.dyh1319.oj.model.dto.question.QuestionUpdateRequest;
import asia.dyh1319.oj.model.entity.Question;
import asia.dyh1319.oj.model.vo.QuestionVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * @className QuestionService
 * @version 1.0
 * @author DYH
 * @since 2024/1/20 16:35
 */
    
public interface QuestionService extends IService<Question> {
    
    /**
     * 新增题目
     * @return 新增题目的id
     */
    long addQuestion(QuestionAddRequest questionAddRequest, HttpServletRequest request);
    
    /**
     * 删除题目
     * @return 是否删除成功
     */
    boolean deleteQuestion(DeleteRequest deleteRequest, HttpServletRequest request);
    
    /**
     * 更新题目
     * @return 是否更新成功
     */
    boolean updateQuestion(QuestionUpdateRequest questionUpdateRequest, HttpServletRequest request);
    
    /**
     * 根据id获取题目
     * @return 题目（脱敏）
     */
    QuestionVO getQuestionVOById(Long id, HttpServletRequest request);
    
    /**
     * 根据id获取题目
     * @return 题目
     */
    Question getQuestionById(Long id, HttpServletRequest request);
    
    /**
     * 分页获取题目列表（脱敏）
     */
    Page<QuestionVO> listQuestionVOByPage(QuestionQueryRequest questionQueryRequest, HttpServletRequest request);
    
    /**
     * 分页获取题目列表
     */
    Page<Question> listQuestionByPage(QuestionQueryRequest questionQueryRequest, HttpServletRequest request);
}
