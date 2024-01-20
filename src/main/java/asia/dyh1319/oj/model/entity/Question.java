package asia.dyh1319.oj.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 题目表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_question")
public class Question implements Serializable {
    
    private static final long serialVersionUID = 2304291969722975644L;
    
    /**
     * 主键，自动递增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 标题，唯一
     */
    @TableField(value = "title")
    private String title;
    
    /**
     * 内容，包含题目详细说明，输入输出格式等信息
     */
    @TableField(value = "content")
    private String content;
    
    /**
     * 难度（easy/normal/hard等）
     */
    @TableField(value = "question_level")
    private String questionLevel;
    
    /**
     * 标签列表（json数组）
     */
    @TableField(value = "tags")
    private String tags;
    
    /**
     * 标准答案
     */
    @TableField(value = "answer")
    private String answer;
    
    /**
     * 提交数
     */
    @TableField(value = "submit_num")
    private Integer submitNum;
    
    /**
     * 通过数
     */
    @TableField(value = "accepted_num")
    private Integer acceptedNum;
    
    /**
     * 判题配置（json对象，存储timeLimit、memoryLimit、stackLimit、判题类型judgeType等）
     */
    @TableField(value = "judge_config")
    private String judgeConfig;
    
    /**
     * 判题用例（json数组，每一个元素中包含一对输入用例和输出用例）
     */
    @TableField(value = "judge_cases")
    private String judgeCases;
    
    /**
     * 点赞数
     */
    @TableField(value = "thumb_num")
    private Integer thumbNum;
    
    /**
     * 收藏数
     */
    @TableField(value = "favour_num")
    private Integer favourNum;
    
    /**
     * 创建用户id
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