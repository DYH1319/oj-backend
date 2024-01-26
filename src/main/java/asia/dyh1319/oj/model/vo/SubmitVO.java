package asia.dyh1319.oj.model.vo;

import asia.dyh1319.oj.judge.codesandbox.model.JudgeInfo;
import asia.dyh1319.oj.model.entity.Submit;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

@Data
public class SubmitVO implements Serializable {
    
    private static final long serialVersionUID = -1313346948842997051L;
    
    /**
     * 主键，自动递增
     */
    private Long id;
    
    /**
     * 编程语言
     */
    private String submitLanguage;
    
    /**
     * 用户代码
     */
    private String code;
    
    /**
     * 判题信息（json对象，存储判题机返回的信息、判题耗时、判题所占空间等）
     */
    private JudgeInfo judgeInfo;
    
    /**
     * 判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）
     */
    private Integer submitStatus;
    
    /**
     * 题目id
     */
    private Long questionId;
    
    /**
     * 提交用户
     */
    private UserVO userVO;
    
    /**
     * 是否逻辑删除
     */
    private Byte isDeleted;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * userVO对象须查库设置
     */
    public static SubmitVO objToVo(Submit submit) {
        if (submit == null) {
            return null;
        }
        SubmitVO submitVO = new SubmitVO();
        BeanUtils.copyProperties(submit, submitVO);
        submitVO.setJudgeInfo(JSONUtil.toBean(submit.getJudgeInfo(), JudgeInfo.class));
        return submitVO;
    }
    
    public static Submit voToObj(SubmitVO submitVO) {
        if (submitVO == null) {
            return null;
        }
        Submit submit = new Submit();
        BeanUtils.copyProperties(submitVO, submit);
        submit.setJudgeInfo(JSONUtil.toJsonStr(submitVO.getJudgeInfo()));
        submit.setUserId(submitVO.userVO.getId());
        return submit;
    }
}