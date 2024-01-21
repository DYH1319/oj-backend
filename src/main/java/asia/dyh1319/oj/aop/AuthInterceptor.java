package asia.dyh1319.oj.aop;

import asia.dyh1319.oj.annotation.AuthCheck;
import asia.dyh1319.oj.common.StatusCode;
import asia.dyh1319.oj.constant.UserConstant;
import asia.dyh1319.oj.exception.BusinessException;
import asia.dyh1319.oj.mapper.UserMapper;
import asia.dyh1319.oj.model.entity.User;
import asia.dyh1319.oj.model.enums.UserRoleEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 权限校验 AOP
 */
@Aspect
@Component
public class AuthInterceptor {
    
    @Resource
    private UserMapper userMapper;
    
    /**
     * 执行拦截
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        UserRoleEnum mustRole = authCheck.mustRole();
        int mustRoleWeight = mustRole.getWeight();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 当前用户
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (user != null && user.getId() != null && user.getId() > 0) {
            user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getId, user.getId()));
        }
        int loginUserWeight = (user == null || user.getId() == null || user.getId() <= 0 ? 0 : UserRoleEnum.getWeightByValue(user.getUserRole()));
        // 若用户权限小于需要的最低权限，则抛出异常
        if (loginUserWeight < mustRoleWeight) {
            if (loginUserWeight == 0) {
                throw new BusinessException(StatusCode.NOT_LOGIN_ERROR, "请先登录");
            } else {
                throw new BusinessException(StatusCode.NO_AUTH_ERROR, "您没有此操作的权限");
            }
        }
        // 通过权限校验，放行
        return joinPoint.proceed();
    }
}

