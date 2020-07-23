package com.example.hello.annotation.yml;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 读 - yml格式
 *
 * @author zhoukai
 * @date 2020/7/22 16:16
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadDataSource {
    String description() default "";
}
