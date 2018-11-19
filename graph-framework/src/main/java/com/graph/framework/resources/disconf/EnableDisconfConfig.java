package com.graph.framework.resources.disconf;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DisconfConfigRegistrar.class)
public @interface EnableDisconfConfig {

    @AliasFor("properties")
    String value() default "";

    @AliasFor("value")
    String properties() default "";

    String scanPackages() default "com.graph";
}
