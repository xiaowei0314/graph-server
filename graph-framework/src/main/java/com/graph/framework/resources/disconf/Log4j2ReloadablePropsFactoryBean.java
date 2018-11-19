package com.graph.framework.resources.disconf;

import com.baidu.disconf.client.addons.properties.ReloadablePropertiesFactoryBean;
import com.google.common.collect.Lists;

public class Log4j2ReloadablePropsFactoryBean extends ReloadablePropertiesFactoryBean {

    public Log4j2ReloadablePropsFactoryBean() {
        super();
        this.setLocations(Lists.newArrayList("log4j2.properties"));
    }
}
