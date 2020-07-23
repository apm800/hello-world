package com.example.hello.controller;

import com.example.hello.entity.UserInfo;
import com.example.hello.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * http://localhost:8080/test/master
 *
 * @author zhoukai
 * @date 2020/7/22 15:28
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private UserInfoService userInfoServiceImpl;

    @RequestMapping("/other")
    public void getByOther() {
        List<UserInfo> userInfos = userInfoServiceImpl.listAll();
        System.out.println("从other库查到的数据==" + userInfos);
    }

    @RequestMapping("/master")
    public void getByMaster() {
        List<UserInfo> userInfos = userInfoServiceImpl.getByMaster();
        System.out.println("从master库查到的数据==" + userInfos);
    }

}
