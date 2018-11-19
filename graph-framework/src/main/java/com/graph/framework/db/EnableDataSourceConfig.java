package com.graph.framework.db;

import com.graph.framework.resources.disconf.DisconfConfigRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 将此注解添加在启动类上，开启读写分离
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration //↓@Order对Configuration无效，暂用此方法保证加载顺序↓
@Import({DisconfConfigRegistrar.class, DataSourceRegistrar.class})
public @interface EnableDataSourceConfig {
}
