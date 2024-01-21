package asia.dyh1319.oj.service.impl;

import asia.dyh1319.oj.mapper.SubmitMapper;
import asia.dyh1319.oj.model.dto.submit.SubmitAddRequest;
import asia.dyh1319.oj.model.entity.Submit;
import asia.dyh1319.oj.service.SubmitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @version 1.0
 * @author DYH
 * @since 2024/1/20 16:33
 */
    
@Service
public class SubmitServiceImpl extends ServiceImpl<SubmitMapper, Submit> implements SubmitService {
    
    @Override
    public long addSubmit(SubmitAddRequest submitAddRequest, HttpServletRequest request) {
        return 0;
    }
}
