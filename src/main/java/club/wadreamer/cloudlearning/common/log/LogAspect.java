package club.wadreamer.cloudlearning.common.log;

import club.wadreamer.cloudlearning.model.auto.OperationLog;
import club.wadreamer.cloudlearning.model.auto.User;
import club.wadreamer.cloudlearning.service.OperationLogService;
import club.wadreamer.cloudlearning.shiro.util.ShiroUtils;
import club.wadreamer.cloudlearning.util.ServletUtils;
import club.wadreamer.cloudlearning.util.StringUtils;
import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName LogAspect 日志切面类
 * @Description TODO
 * @Author bear
 * @Date 2020/3/1 13:40
 * @Version 1.0
 **/
@Aspect
@Component
@EnableAsync
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private OperationLogService operationLogService;

    @Pointcut("@annotation(club.wadreamer.cloudlearning.common.log.Log)")
    public void logPointCut() {
    }

    @AfterReturning(pointcut = "logPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        handleLog(joinPoint, null);
    }

    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e);
    }

    @Async
    protected void handleLog(final JoinPoint joinPoint, final Exception e) {
        try {
            //获取注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if(controllerLog == null){
                return;
            }

            //获取当前用户
            User currentUser = ShiroUtils.getUser();
            //创建数据库的日志
            OperationLog operationLog = new OperationLog();
            //设置请求地址
            operationLog.setOperUrl(ServletUtils.getRequest().getRequestURI());

            if(currentUser != null){
                operationLog.setOperName(currentUser.getUsername());
            }
            if(e != null){
                operationLog.setErrorMsg(StringUtils.substring(e.getMessage(),0,2000));
            }
            //设置方法名
            String className = joinPoint.getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operationLog.setMethod(className + "." + methodName + "()");
            //设置操作日期
            operationLog.setOperTime(new Date());
            //设置操作参数和操作名
            getControllerMethodDescription(controllerLog,operationLog);

            //写入数据库
            operationLogService.insertSelective(operationLog);
        }catch (Exception exp){
         log.error("==前置通知异常");
         log.error("异常信息:{}",exp.getMessage());
         exp.printStackTrace();
        }
    }

    public void getControllerMethodDescription(Log log, OperationLog operationLog) throws Exception {
        operationLog.setTitle(log.title());//设置操作名
        //保存request参数和值
        if(log.isSaveRequestData()){
            setRequestValue(operationLog);
        }
    }

    private void setRequestValue(OperationLog operationLog) {
        Map<String, String[]> map = ServletUtils.getRequest().getParameterMap();
        Gson gson = new Gson();
        String params = gson.toJson(map);
        operationLog.setOperParam(StringUtils.substring(params, 0, 255));
    }

    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();//获取接入点签名
        MethodSignature methodSignature = (MethodSignature) signature;//获取方法签名
        Method method = methodSignature.getMethod();//获取方法

        if (method != null) {
            return method.getAnnotation(Log.class);//获取注解
        }
        return null;
    }
}
