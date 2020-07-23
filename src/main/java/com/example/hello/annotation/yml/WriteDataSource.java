package com.example.hello.annotation.yml;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 写 - yml格式
 *
 * @author zhoukai
 * @date 2020/7/22 16:18
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface WriteDataSource {
    String description() default "";
}
