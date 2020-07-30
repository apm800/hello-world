//package com.example.hello.datasource.yml;
//
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * 如果使用properties格式配置,需要将此注掉
// *
// * @author zhoukai
// * @date 2020/7/23 10:34
// */
//public class MyAbstractRoutingDataSource extends AbstractRoutingDataSource {
//    private final int dataSourceNumber;
//    private AtomicInteger count = new AtomicInteger(0);
//
//    public MyAbstractRoutingDataSource(int dataSourceNumber) {
//        this.dataSourceNumber = dataSourceNumber;
//    }
//
//    @Override
//    protected Object determineCurrentLookupKey() {
//        String typeKey = DataSourceContextHolder.getJdbcType();
//        //如果没有注解 默认为 读
//        if (null == typeKey) {
//            return DataSourceType.read.getType();
//        }
//        if (DataSourceType.write.getType().equals(typeKey)) {
//            return DataSourceType.write.getType();
//        }
//
//        // 读 简单负载均衡
//        int number = count.getAndAdd(1);
//        int lookupKey = number % dataSourceNumber;
//        return new Integer(lookupKey);
//    }
//}
