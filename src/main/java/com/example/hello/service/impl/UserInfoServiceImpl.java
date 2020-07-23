package com.example.hello.service.impl;

import com.example.hello.annotation.properties.TargetDataSource;
import com.example.hello.datasource.properties.DataSourceKey;
import com.example.hello.dao.UserInfoMapper;
import com.example.hello.entity.UserInfo;
import com.example.hello.service.UserInfoService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhoukai
 * @date 2020/7/22 15:29
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
    private static final Logger LOG = Logger.getLogger(UserInfoServiceImpl.class);
    @Resource
    private UserInfoMapper userInfoMapper;

    @TargetDataSource(dataSourceKey = DataSourceKey.DB_OTHER)
    @Override
    public List<UserInfo> listAll() {
        return userInfoMapper.listAll();
    }

    @TargetDataSource(dataSourceKey = DataSourceKey.DB_MASTER)
    @Override
    public List<UserInfo> getByMaster() {
        return userInfoMapper.listAll();
    }

    @TargetDataSource(dataSourceKey = DataSourceKey.DB_OTHER)
    @Override
    public int insert(UserInfo userInfo) {
        return userInfoMapper.insert(userInfo);
    }
}
