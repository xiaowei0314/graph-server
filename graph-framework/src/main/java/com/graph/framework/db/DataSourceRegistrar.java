package com.graph.framework.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 注入dataSource逻辑
 */
public class DataSourceRegistrar extends ApplicationObjectSupport implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceRegistrar.class);
    private static final String DATASOURCE_TYPE_DEFAULT = "org.apache.tomcat.jdbc.pool.DataSource";
    private static final String PREFIX_DEFAULT = "spring.datasource";
    private static final String PREFIX_SLAVE = "slave.datasource";
    private static Map<String, DataSource> slaves = new HashMap<String, DataSource>();
    private Environment environment;

    private static DataSource registDefault(Environment env) {

        Binder binder = Binder.get(env);
        Map<String, String> map = binder.bind(PREFIX_DEFAULT, Map.class).get();
        return build(map);

    }

    private static void registSlaves(Environment env) {

        Binder binder = Binder.get(env);
        Map<String, String> map = binder.bind(PREFIX_SLAVE, Map.class).get();
        if (map != null) {
            String names = map.get("names");
            if (names != null) {
                for (String slaveName : names.split(",")) {
                    Map<String, String> slaveMap = binder.bind(PREFIX_SLAVE + "." + slaveName, Map.class).get();
                    slaves.put(slaveName, build(slaveMap));
                }
            }
        }
    }

    private static DataSource build(Map<String, String> map) {

        try {
            String dsType = map.get("type") == null ? DATASOURCE_TYPE_DEFAULT : map.get("type");
            Class<? extends DataSource> dataSourceType = (Class<? extends DataSource>) Class.forName(dsType);
            DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(map.get("driver-class-name")).url(map.get("url"))
                    .username(map.get("username")).password(map.get("password")).type(dataSourceType);
            return factory.build();
            // other settings bu ji dao zhong me she zhi
        } catch (Exception e) {
            logger.error("DataSource build error: " + e.getMessage(), e);
        }

        return null;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {

        // 主数据源
        DataSource defaultDataSource = registDefault(environment);
        // 从数据源
        registSlaves(environment);

        // 所有的数据源<DataSourceName, DataSourceValue>
        Map<String, DataSource> targetDataSources = new HashMap<String, DataSource>();
        targetDataSources.put("w", defaultDataSource);
        targetDataSources.putAll(slaves);
        // DataSourceRouter中注入主数据源+从数据源
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DataSourceRouter.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource); // 默认数据源
        mpv.addPropertyValue("targetDataSources", targetDataSources); // 所有的数据源
        beanDefinitionRegistry.registerBeanDefinition("dataSource", beanDefinition);
    }

    @Override
    public void setEnvironment(Environment environment) {

        this.environment = environment;
    }
}
