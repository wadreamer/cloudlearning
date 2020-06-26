package club.wadreamer.cloudlearning.common.log;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块
     */
    String title() default "";

    /**
     * 功能
     */
    String action() default "";

    /**
     * 渠道
     */
    String channel() default "1";//1后台

    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;
}
