package club.wadreamer.cloudlearning.common.dataSources;

import club.wadreamer.cloudlearning.util.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 多数据源处理
 *
 * @author fuce
 * @ClassName: DataSourceAspect
 * @date 2019-12-07 18:40
 */
@Aspect
@Component
@Order(1)
//@EnableAsync
public class DataSourceAspect {
    //private static final Logger log = LoggerFactory.getLogger(DataSourceAspect.class);

    //@Pointcut("@annotation(.......))-->表明了某个注解的所有方法均为切点
    @Pointcut("@annotation(club.wadreamer.cloudlearning.common.dataSources.DataSource)")
    public void dsPointCut() {//定义切面，方法名
    }

    @Around("dsPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature(); // 获取方法签名
        Method method = signature.getMethod(); // 通过方法签名获得方法名
        DataSource dataSource = method.getAnnotation(DataSource.class); // 获取注解在该方法上的value值，默认是主数据库master

        if (null != dataSource) {
            DataSourceContextHolder.setDataSource(dataSource.value().name());//设置数据源
        }
        try {
            return point.proceed();
        } finally {
            DataSourceContextHolder.clearDataSource(); // 销毁数据源,在执行方法之后
        }
    }

    /**
     * 获取需要切换的数据源
     */
    public DataSource getDataSource(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Class<? extends Object> targetClass = point.getTarget().getClass();
        DataSource targetDataSource = targetClass.getAnnotation(DataSource.class);
        if (StringUtils.isNotNull(targetDataSource)) {
            return targetDataSource;
        } else {
            Method method = signature.getMethod();
            DataSource dataSource = method.getAnnotation(DataSource.class);
            return dataSource;
        }
    }
}
