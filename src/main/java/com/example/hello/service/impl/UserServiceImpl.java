package com.example.hello.service.impl;

import com.example.hello.dao.UserDao;
import com.example.hello.entity.User;
import com.example.hello.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhoukai
 * @date 2020/7/22 15:29
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);
    @Resource
    private UserDao userDao;

    @Override
    public List<User> listAll() {
        return userDao.listAll();
    }

    @Override
    public int insert(User user) {
        return userDao.insert(user);
    }
}
