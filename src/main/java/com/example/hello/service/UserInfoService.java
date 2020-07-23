package com.example.hello.service;

import com.example.hello.entity.UserInfo;

import java.util.List;

/**
 * @author zhoukai
 * @date 2020/7/22 15:29
 */
public interface UserInfoService {

    List<UserInfo> listAll();

    List<UserInfo> getByMaster();

    int insert(UserInfo userInfo);
}
