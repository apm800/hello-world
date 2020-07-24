package com.example.hello.service.impl;

import com.example.hello.annotation.properties.TargetDataSource;
import com.example.hello.annotation.yml.ReadDataSource;
import com.example.hello.annotation.yml.WriteDataSource;
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

    //不加注解默认为 读
    @ReadDataSource(description = "READ")
//    @WriteDataSource(description="WRITE")
    @Override
    public List<UserInfo> getByRead() {
        return userInfoDao.getByRead();
    }

    //    @ReadDataSource(description="READ")
    @WriteDataSource(description = "WRITE")
    //@Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT,readOnly=true)
    @Override
    public List<UserInfo> getByWrite() {
        return userInfoDao.getByWrite();
    }

    @TargetDataSource(dataSourceKey = DataSourceKey.DB_OTHER)
    @Override
    public int insert(UserInfo userInfo) {
        return userInfoDao.insert(userInfo);
    }
}
