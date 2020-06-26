package club.wadreamer.cloudlearning.common.exception;

import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.util.ServletUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName GlobalExceptionResolver 全局异常处理
 * @Description TODO
 * @Author bear
 * @Date 2020/3/1 21:32
 * @Version 1.0
 **/
@RestControllerAdvice
public class GlobalExceptionResolver {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    /*
     * @Author bear
     * @Description 权限校验失败 如果请求为ajax返回json，普通请求跳转页面
     * @Date 21:35 2020/3/1
     * @Param [request, e]
     * @return java.lang.Object
     **/
    @ExceptionHandler(AuthorizationException.class)
    public Object handleAuthorizationException(HttpServletRequest request, AuthorizationException e) {
        //开发环境打印异常，正式环境请注销
        logger.error(" 权限校验异常》》" + e.getMessage(), e);

        if (ServletUtils.isAjaxRequest(request)) {
            return AjaxResult.error(e.getMessage());
        } else {
            ModelAndView mv;
            //shiro异常拦截
            if (e instanceof UnauthorizedException) {
                //未授权异常
                mv = new ModelAndView("/error/403");
                return mv;
            } else if (e instanceof UnauthenticatedException) {
                //未认证异常
                mv = new ModelAndView("/error/403");
                return mv;
            } else {
                mv = new ModelAndView();
                return mv;
            }
        }
    }

    @ExceptionHandler(BindException.class)
    public AjaxResult validatedBindException(BindException e) {
        logger.error("自定义验证异常", e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return AjaxResult.error(message);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public AjaxResult handleException(HttpRequestMethodNotSupportedException e) {
        logger.error("请求方式不支持异常:", e);
        return AjaxResult.error("不支持' " + e.getMethod() + "'请求");
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handle(Exception e) {
        logger.error("系统异常:", e);
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", e.getMessage());
        mv.setViewName("/error/999");
        return mv;
    }

    @ExceptionHandler(RuntimeException.class)
    public AjaxResult notFount(RuntimeException e) {
        logger.error("运行时异常:", e);
        return AjaxResult.error("运行时异常:" + e.getMessage());
    }
}
