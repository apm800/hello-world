//package com.example.hello.datasource.yml;
//
//import com.example.hello.annotation.yml.ReadDataSource;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Method;
//
///**
// * 如果使用properties格式配置,需要将此注掉
// *
// * @author zhoukai
// * @date 2020/7/23 10:50
// */
//@Aspect
//@Component
//public class DataSourceAop {
//    private static Logger LOG = LogManager.getLogger(DataSourceAop.class);
//
//    @Pointcut("@annotation(com.example.hello.annotation.yml.WriteDataSource)")
//    public void writeMethod() {
//        System.out.println("");
//    }
//
//    @Pointcut("@annotation(com.example.hello.annotation.yml.ReadDataSource)")
//    public void readMethod() {
//        System.out.println("");
//    }
//
//    @Before("writeMethod()")
//    public void beforeWrite(JoinPoint point) {
//        DataSourceContextHolder.write();
//        String className = point.getTarget().getClass().getName();
//        String methodName = point.getSignature().getName();
//        System.out.println("开始执行:" + className + "." + methodName + "()方法...");
//        LOG.info("dataSource切换到：Write");
//    }
//
//    @Before("readMethod()")
//    public void beforeRead(JoinPoint point) throws ClassNotFoundException {
//        //设置数据库为读数据
//        DataSourceContextHolder.read();
//        /*spring AOP测试代码*/
//
//        //根据切点获取当前调用的类名
//        String currentClassName = point.getTarget().getClass().getName();
//        //根据切点获取当前调用的类方法
//        String methodName = point.getSignature().getName();
//        //根据切点获取当前类方法的参数
//        Object[] args = point.getArgs();
//        System.out.println("开始执行:" + currentClassName + "." + methodName + "()方法...");
//        //根据反射获取当前调用类的实例
//        Class reflexClassName = Class.forName(currentClassName);
//        //获取该实例的所有方法
//        Method[] methods = reflexClassName.getMethods();
//        for (Method method : methods) {
//            if (method.getName().equals(methodName)) {
//                //获取该实例方法上注解里面的描述信息
//                String description = method.getAnnotation(ReadDataSource.class).description();
//                System.out.println("description:" + description);
//            }
//        }
//        LOG.info("dataSource切换到：Read");
//    }
//}
