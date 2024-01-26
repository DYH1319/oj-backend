package asia.dyh1319.oj.service;

import asia.dyh1319.oj.model.dto.submit.SubmitAddRequest;
import asia.dyh1319.oj.model.entity.Submit;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @version 1.0
 * @author DYH
 * @since 2024/1/20 16:33
 */

public interface SubmitService extends IService<Submit> {
    
    /**
     * 新建提交
     *
     * @return 新提交的id
     */
    long addSubmit(SubmitAddRequest submitAddRequest, HttpServletRequest request);
    
    /**
     * 获取所有的可用编程语言
     */
    List<List<String>> fetchLanguages();
}
