package com.example.hello.datasource.yml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 如果使用properties格式配置,需要将此注掉
 * @author zhoukai
 * @date 2020/7/23 10:31
 */
public class DataSourceContextHolder {
    private static Logger log = LoggerFactory.getLogger(DataSourceContextHolder.class);
    private static final ThreadLocal<String> local = new ThreadLocal<String>();
    public static ThreadLocal<String> getLocal() {
        return local;
    }
    /**
     * 读可能是多个库
     */
    public static void read() {
        local.set(DataSourceType.read.getType());
        System.out.println("==:" + DataSourceType.read.getType());
        log.info("数据库切换到=读库...");
    }
    /**
     * 写只有一个库
     */
    public static void write() {
        local.set(DataSourceType.write.getType());
        log.info("数据库切换到=写库...");
    }
    public static String getJdbcType() {
        return local.get();
    }
}
