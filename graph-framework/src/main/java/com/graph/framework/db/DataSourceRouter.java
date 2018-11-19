package com.graph.framework.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

public class DataSourceRouter extends AbstractRoutingDataSource {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceRouter.class);


    //返回值是项目中所要用的DataSource的key值
    // 拿到该key后就可以在resolvedDataSource中取出对应的DataSource
    // 如果key找不到对应的DataSource就使用默认的数据源
    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        Object dataSource = DataSourceHolder.getDataSourceName();
        if (logger.isDebugEnabled()) {
            logger.debug("choose datasource : " + dataSource);
        }
        return dataSource;
    }
}
