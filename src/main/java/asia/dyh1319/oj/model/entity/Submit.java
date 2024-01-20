package asia.dyh1319.oj.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 题目提交表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_submit")
public class Submit implements Serializable {
    
    private static final long serialVersionUID = -1313346948842997451L;
    
    /**
     * 主键，自动递增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 编程语言
     */
    @TableField(value = "submit_language")
    private String submitLanguage;
    
    /**
     * 用户代码
     */
    @TableField(value = "code")
    private String code;
    
    /**
     * 判题信息（json对象，存储判题机返回的信息、判题耗时、判题所占空间等）
     */
    @TableField(value = "judge_info")
    private String judgeInfo;
    
    /**
     * 判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）
     */
    @TableField(value = "submit_status")
    private Integer submitStatus;
    
    /**
     * 题目id
     */
    @TableField(value = "question_id")
    private Long questionId;
    
    /**
     * 提交用户id
     */
    @TableField(value = "user_id")
    private Long userId;
    
    /**
     * 是否逻辑删除
     */
    @TableLogic
    @TableField(value = "is_deleted")
    private Byte isDeleted;
    
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;
    
    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;
}