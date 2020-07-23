package com.example.hello.service.impl;

import com.example.hello.annotation.properties.TargetDataSource;
import com.example.hello.datasource.properties.DataSourceKey;
import com.example.hello.dao.UserInfoDao;
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
    private UserInfoDao userInfoDao;

    @TargetDataSource(dataSourceKey = DataSourceKey.DB_MASTER)
    @Override
    public List<UserInfo> getByMaster() {
        return userInfoDao.listAll();
    }

    @TargetDataSource(dataSourceKey = DataSourceKey.DB_OTHER)
    @Override
    public List<UserInfo> getByOther() {
        return userInfoDao.listAll();
    }

    @TargetDataSource(dataSourceKey = DataSourceKey.DB_OTHER)
    @Override
    public int insert(UserInfo userInfo) {
        return userInfoDao.insert(userInfo);
    }
}
