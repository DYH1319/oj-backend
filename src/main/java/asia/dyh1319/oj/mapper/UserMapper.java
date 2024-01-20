package asia.dyh1319.oj.mapper;

import asia.dyh1319.oj.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author DYH
 * @version 1.0
 * @className UserMapper
 * @since 2024/1/20 13:45
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {
}