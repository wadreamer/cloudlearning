package club.wadreamer.cloudlearning.common.dataSources;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于类或方法上，优先级：方法>类
 *
 * @author fuce
 * @ClassName: DataSource
 * @date 2019-12-06 21:15
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)//声明注解的保留期限
public @interface DataSource {
    /**
     * 切换数据源名称，即声明注解成员value,
     * @interface声明的每一个方法实际上是声明了一个配置参数
     * default DataSourceType.MASTER--->设置value的默认值
     */
    DataSourceType value() default DataSourceType.MASTER;
}
