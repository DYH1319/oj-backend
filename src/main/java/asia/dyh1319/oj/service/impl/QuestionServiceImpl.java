package asia.dyh1319.oj.service.impl;

import asia.dyh1319.oj.mapper.QuestionMapper;
import asia.dyh1319.oj.model.entity.Question;
import asia.dyh1319.oj.service.QuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @className QuestionServiceImpl
 * @version 1.0
 * @author DYH
 * @since 2024/1/20 16:35
 */
    
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

}
