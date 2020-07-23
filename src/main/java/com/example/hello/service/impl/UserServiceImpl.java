package com.example.hello.service.impl;

import com.example.hello.dao.UserMapper;
import com.example.hello.entity.User;
import com.example.hello.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhoukai
 * @date 2020/7/22 15:29
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);
    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> listAll() {
        return userMapper.listAll();
    }

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }
}
