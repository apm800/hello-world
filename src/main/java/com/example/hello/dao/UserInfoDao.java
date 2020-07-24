package com.example.hello.dao;

import com.example.hello.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhoukai
 * @date 2020/7/22 15:29
 */
@Mapper
public interface UserInfoDao {
    List<UserInfo> listAll();

    int insert(UserInfo userInfo);

    List<UserInfo> getByRead();

    List<UserInfo> getByWrite();
}
