package com.graph.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class})
@ComponentScan(basePackages = {"com.graph.server.*"})
//@EnableDataSourceConfig //添加此注解，则不需要DataSourceConfiguration类
//@EnableDisconfConfig("application.properties,archetype.properties")
public class ApplicationBootstrap extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApplicationBootstrap.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationBootstrap.class,args);
    }
}
