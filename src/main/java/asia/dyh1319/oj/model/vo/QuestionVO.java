package asia.dyh1319.oj.model.vo;

import asia.dyh1319.oj.judge.codesandbox.model.JudgeConfig;
import asia.dyh1319.oj.model.entity.Question;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author DYH
 * @version 1.0
 * @since 2024/1/20 18:28
 */
@Data
public class QuestionVO implements Serializable {
    
    private static final long serialVersionUID = -1925469821819535819L;
    
    /**
     * 主键，自动递增
     */
    private Long id;
    
    /**
     * 标题，唯一
     */
    private String title;
    
    /**
     * 内容，包含题目详细说明，输入输出格式等信息
     */
    private String content;
    
    /**
     * 难度（easy/normal/hard等）
     */
    private String questionLevel;
    
    /**
     * 标签列表（json数组）
     */
    private List<String> tags;
    
    /**
     * 提交数
     */
    private Integer submitNum;
    
    /**
     * 通过数
     */
    private Integer acceptedNum;
    
    /**
     * 判题配置（json对象，存储timeLimit、memoryLimit、stackLimit、判题类型judgeType等）
     */
    private JudgeConfig judgeConfig;
    
    /**
     * 点赞数
     */
    private Integer thumbNum;
    
    /**
     * 收藏数
     */
    private Integer favourNum;
    
    /**
     * 创建用户id
     */
    private UserVO userVO;
    
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
    
    /**
     * userVO须查库设置
     */
    public static QuestionVO objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(question, questionVO);
        questionVO.setTags(JSONUtil.toList(question.getTags(), String.class));
        questionVO.setJudgeConfig(JSONUtil.toBean(question.getJudgeConfig(), JudgeConfig.class));
        return questionVO;
    }
    
    public static Question voToObj(QuestionVO questionVO) {
        if (questionVO == null) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionVO, question);
        question.setTags(JSONUtil.toJsonStr(questionVO.getTags()));
        question.setJudgeConfig(JSONUtil.toJsonStr(questionVO.getJudgeConfig()));
        question.setUserId(questionVO.userVO.getId());
        return question;
    }
}
