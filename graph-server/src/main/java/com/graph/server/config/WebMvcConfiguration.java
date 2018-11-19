package com.graph.server.config;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static com.alibaba.fastjson.serializer.SerializerFeature.*;

/**
 * Created by MaJingcao on 2018/3/9.
 * Copyright by syswin
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    /**
     * json序列化
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig converterConfig = new FastJsonConfig();
        converterConfig.setSerializerFeatures(
                PrettyFormat,
                WriteNullListAsEmpty,
                WriteNullStringAsEmpty,
                WriteMapNullValue);
        converter.setFastJsonConfig(converterConfig);
        converters.add(converter);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://**")
                .allowedMethods("PUT", "DELETE", "POST", "GET", "OPTIONS")
                .allowedHeaders("x-requested-with", "content-type", "accept", "origin", "X-Toon-User-ID", "X-Toon-User-Token", "X-Toon-User-Agent")
                .allowCredentials(true).maxAge(3600);
    }

//    @Bean
//    ApiAuthInterceptor apiAuthInterceptor(){
//        return new ApiAuthInterceptor();
//    }

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(apiAuthInterceptor()).addPathPatterns("/api/**");
//        registry.addInterceptor(mgrAuthInterceptor()).addPathPatterns("/mgr/**");
//        registry.addInterceptor(operAuthInterceptor()).addPathPatterns("/oper/**");
//        registry.addInterceptor(operAuthInterceptor()).addPathPatterns("/fileHandling/**");
    }

}
