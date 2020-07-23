package com.example.hello.datasource.properties;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author zhoukai
 * @date 2020/7/22 15:28
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
    private static final Logger LOG = Logger.getLogger(DynamicRoutingDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        LOG.info("当前数据源：{}" + DynamicDataSourceContextHolder.get());
        return DynamicDataSourceContextHolder.get();
    }
}
