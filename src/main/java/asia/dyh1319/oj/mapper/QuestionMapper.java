package asia.dyh1319.oj.mapper;

import asia.dyh1319.oj.model.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * @className QuestionMapper
 * @version 1.0
 * @author DYH
 * @since 2024/1/20 16:35
 */
    
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
}