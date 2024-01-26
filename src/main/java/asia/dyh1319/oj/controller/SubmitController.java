package asia.dyh1319.oj.controller;

import asia.dyh1319.oj.annotation.AuthCheck;
import asia.dyh1319.oj.common.BaseResponse;
import asia.dyh1319.oj.common.StatusCode;
import asia.dyh1319.oj.exception.BusinessException;
import asia.dyh1319.oj.model.dto.submit.SubmitAddRequest;
import asia.dyh1319.oj.model.enums.UserRoleEnum;
import asia.dyh1319.oj.service.SubmitService;
import asia.dyh1319.oj.utils.ResponseUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * submit 控制器
 */
@RestController
@RequestMapping("/submit")
public class SubmitController {
    
    @Resource
    private SubmitService submitService;
    
    /**
     * 新增提交
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserRoleEnum.USER)
    public BaseResponse<Long> addSubmit(@RequestBody SubmitAddRequest submitAddRequest, HttpServletRequest request) {
        if (submitAddRequest == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "新建提交请求为空");
        }
        long res = submitService.addSubmit(submitAddRequest, request);
        return ResponseUtils.success(res, "提交成功");
    }
    
    /**
     * 获取所有的可用编程语言
     */
    @GetMapping("/fetch/languages")
    @AuthCheck(mustRole = UserRoleEnum.USER)
    public BaseResponse<List<List<String>>> fetchLanguages() {
        List<List<String>> res = submitService.fetchLanguages();
        return ResponseUtils.success(res, "获取所有的可用编程语言成功");
    }
}
