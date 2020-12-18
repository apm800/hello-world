package com.example.hello.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoukai
 * @date 2020/7/22 15:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    //@Setter 为该类的属性提供set方法
    //@Getter 为该类的属性提供get方法
    //@ToString 提供toString方法
    //@EqualsAndHashCode 提供equals和hashCode方法
    //@NoArgsConstructor 无参构造
    //@AllArgsConstructor 全参构造
    //@RequiredArgsConstructor 制定参数构造
    //@Cleanup 注解需要放在流的声明上，再也不用因为忘记finally/try/catch而烦恼了
    //@Data 大哥，相当于@ToString,@EqualsAndHashCode,Getter以及所有非final字段的@Setter,@RequiredArgsConstructor
    //@Builder 建造者模式

    private String id;
    private String name;
    private String passWord;
    private String email;
    private int isAdmin;
}
