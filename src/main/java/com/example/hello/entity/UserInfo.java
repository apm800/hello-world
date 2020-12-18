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
public class UserInfo {
    private int id;
    private String name;
    private String email;
}
