package com.graph.framework.log;

import java.lang.annotation.*;

/**
 * Created by rocky on 2018/5/23.
 * <p>
 * 说明：需要使用日志的Controller方法上加上此注解即可
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebLog {

    String value() default "";

}
