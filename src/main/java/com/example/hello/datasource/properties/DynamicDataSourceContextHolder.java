package com.example.hello.datasource.properties;

import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author zhoukai
 * @date 2020/7/23 15:21
 */
public class DynamicDataSourceContextHolder {
    private static final Logger LOG = LogManager.getLogger(DynamicDataSourceContextHolder.class);

    private static final ThreadLocal<DataSourceKey> currentDataSource = new ThreadLocal<>();

    /**
     * 清除当前数据源
     */
    public static void clear() {
        currentDataSource.remove();
    }

    /**
     * 获取当前使用的数据源
     *
     * @return 当前使用数据源的ID
     */
    public static DataSourceKey get() {
        return currentDataSource.get();
    }

    /**
     * 设置当前使用的数据源
     *
     * @param value 需要设置的数据源ID
     */
    public static void set(DataSourceKey value) {
        currentDataSource.set(value);
    }

    /**
     * 设置从从库读取数据
     */
    public static void setSlave() {
        if (RandomUtils.nextInt(0, 2) > 0) {
            DynamicDataSourceContextHolder.set(DataSourceKey.DB_SLAVE2);
        } else {
            DynamicDataSourceContextHolder.set(DataSourceKey.DB_SLAVE1);
        }
    }
}
