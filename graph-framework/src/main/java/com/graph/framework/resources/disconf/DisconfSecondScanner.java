package com.graph.framework.resources.disconf;

import com.baidu.disconf.client.DisconfMgrBeanSecond;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class DisconfSecondScanner extends DisconfMgrBeanSecond implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        super.init();
    }
}
