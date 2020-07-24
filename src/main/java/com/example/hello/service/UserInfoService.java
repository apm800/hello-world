package com.example.hello.service;

import com.example.hello.entity.UserInfo;

import java.util.List;

/**
 * @author zhoukai
 * @date 2020/7/22 15:29
 */
public interface UserInfoService {

    List<UserInfo> getByMaster();

    List<UserInfo> getByOther();

    List<UserInfo> getByRead();

    List<UserInfo> getByWrite();

    int insert(UserInfo userInfo);
}
