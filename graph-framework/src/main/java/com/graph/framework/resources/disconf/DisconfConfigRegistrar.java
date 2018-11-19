package com.graph.framework.resources.disconf;

import com.graph.framework.utils.BeanRegistrationUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

public class DisconfConfigRegistrar implements ImportBeanDefinitionRegistrar {

    public DisconfConfigRegistrar() {
    }

    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableDisconfConfig.class.getName()));
        String packages = attributes.getString("scanPackages");
        String propertyNames = attributes.getString("properties");
        DisconfPropertySourceProcessor.setPackage(packages);
        DisconfPropertySourceProcessor.setProperties(propertyNames);
        BeanRegistrationUtils.registerBeanDefinitionIfNotExists(beanDefinitionRegistry, DisconfPropertySourceProcessor.class.getName(), DisconfPropertySourceProcessor.class);
        BeanRegistrationUtils.registerBeanDefinitionIfNotExists(beanDefinitionRegistry, DisconfSecondScanner.class.getName(), DisconfSecondScanner.class);
        BeanRegistrationUtils.registerBeanDefinitionIfNotExists(beanDefinitionRegistry, Log4j2ReloadablePropsFactoryBean.class.getName(), Log4j2ReloadablePropsFactoryBean.class);
    }
}
