//package com.example.hello.configuration.yml;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 如果使用properties格式配置,需要将此注掉
// *
// * @author zhoukai
// * @date 2020/7/23 10:31
// */
//@Configuration
//public class DataSourceConfiguration {
//    private static Logger log = LoggerFactory.getLogger(DataSourceConfiguration.class);
//
//    @Value("${spring.datasource.type}")
//    private Class<? extends DataSource> dataSourceType;
//
//    @Primary
//    @Bean(name = "writeDataSource", destroyMethod = "close", initMethod = "init")
//    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
//    public DataSource writeDataSource() {
//        log.info("-------------------- writeDataSource init ---------------------");
//        return DataSourceBuilder.create().type(dataSourceType).build();
//    }
//
//    /**
//     * 需要配置
//     * 有多少个从库就要配置多少个
//     *
//     * @return
//     */
//    @Bean(name = "readDataSource0", destroyMethod = "close", initMethod = "init")
//    @ConfigurationProperties(prefix = "spring.datasource.druid.other")
//    public DataSource otherDataSource() {
//        log.info("-------------------- otherDataSource  init ---------------------");
//        return DataSourceBuilder.create().type(dataSourceType).build();
//    }
//
//    @Bean(name = "readDataSource1", destroyMethod = "close", initMethod = "init")
//    @ConfigurationProperties(prefix = "spring.datasource.druid.slave1")
//    public DataSource readDataSourceOne() {
//        log.info("-------------------- readDataSource One init ---------------------");
//        return DataSourceBuilder.create().type(dataSourceType).build();
//    }
//
//    @Bean(name = "readDataSource2", destroyMethod = "close", initMethod = "init")
//    @ConfigurationProperties(prefix = "spring.datasource.druid.slave2")
//    public DataSource readDataSourceTwo() {
//        log.info("-------------------- readDataSource Two init ---------------------");
//        return DataSourceBuilder.create().type(dataSourceType).build();
//    }
//
//    /**
//     * 这里的list是多个从库的情况下为了实现简单负载均衡
//     *
//     * @return
//     * @throws SQLException
//     */
//    @Bean("readDataSources")
//    public List<DataSource> readDataSources() throws SQLException {
//        List<DataSource> dataSources = new ArrayList<>();
//        //需要配置
//        dataSources.add(readDataSourceOne());
//        dataSources.add(readDataSourceTwo());
//        return dataSources;
//    }
//}
