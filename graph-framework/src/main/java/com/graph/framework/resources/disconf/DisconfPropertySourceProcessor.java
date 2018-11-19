package com.graph.framework.resources.disconf;

import com.baidu.disconf.client.DisconfMgrBean;
import com.baidu.disconf.client.common.model.DisconfCenterFile;
import com.baidu.disconf.client.store.inner.DisconfCenterStore;
import com.graph.framework.resources.ConfigPropertySourceFactory;
import com.graph.framework.resources.MapConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.util.Map;

public class DisconfPropertySourceProcessor extends DisconfMgrBean implements EnvironmentAware, DisposableBean {
    private static String scanPackages;
    private static String properties;
    private ConfigurableEnvironment environment;
    private ConfigPropertySourceFactory configPropertySourceFactory = new ConfigPropertySourceFactory();

    public DisconfPropertySourceProcessor() {
    }

    public static void setPackage(String packages) {
        scanPackages = packages;
    }

    public static void setProperties(String propertyNames) {
        properties = propertyNames;
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        this.setScanPackage(scanPackages);
        super.postProcessBeanDefinitionRegistry(beanDefinitionRegistry);
        this.addProperties2Env();
    }

    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment)environment;
    }

    private void addProperties2Env() {
        CompositePropertySource composite = new CompositePropertySource("disconfProperties");
        if(StringUtils.isNotEmpty(properties)) {
            String[] propertyNames = properties.split(",");
            String[] pn = propertyNames;
            int plength = propertyNames.length;

            for(int i = 0; i < plength; ++i) {
                String propertyName = pn[i];
                DisconfCenterFile disconfCenterFile = (DisconfCenterFile)DisconfCenterStore.getInstance().getConfFileMap().get(propertyName);
                Map<String, Object> kv = disconfCenterFile.getKV();
                MapConfig config = new MapConfig(kv);
                composite.addPropertySource(this.configPropertySourceFactory.getConfigPropertySource(propertyName, config));
                if(this.environment.getPropertySources().contains(propertyName)) {
                    this.environment.getPropertySources().addAfter(propertyName, composite);
                } else {
                    this.environment.getPropertySources().addFirst(composite);
                }
            }
        }

    }
}
