package com.example.hello.annotation.properties;

import com.example.hello.datasource.properties.DataSourceKey;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 读写分离 - properties格式
 * 可以加在实现方法上,也可以加在接口的抽象方法上
 * @author zk
 * @date 2020/3/30 11:31
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetDataSource {
    DataSourceKey dataSourceKey() default DataSourceKey.DB_MASTER;
}
