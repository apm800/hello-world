package com.example.hello.dao;

import com.example.hello.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhoukai
 * @date 2020/7/22 15:29
 */
@Mapper
public interface UserMapper {
    List<User> listAll();

    int insert(User user);
}
