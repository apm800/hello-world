package com.example.hello.service;

import com.example.hello.entity.User;

import java.util.List;

/**
 * @author zhoukai
 * @date 2020/7/22 15:29
 */
public interface UserService {
    List<User> listAll();

    int insert(User user);
}
