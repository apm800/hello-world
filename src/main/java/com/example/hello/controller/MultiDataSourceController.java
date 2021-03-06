package com.example.hello.controller;

import com.example.hello.entity.UserInfo;
import com.example.hello.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * http://localhost:8080/test/master
 *
 * @author zhoukai
 * @date 2020/7/22 15:28
 */
@Api(value = "MultiDataSourceController", description = "读写分离")
@RestController
@RequestMapping("/test")
public class MultiDataSourceController {
    @Autowired
    private UserInfoService userInfoServiceImpl;

    @ApiOperation(value = "主库-读取数据")
    @RequestMapping(method = RequestMethod.GET, value = "/master")
    @ResponseBody
    public List<UserInfo> getByMaster() {
        List<UserInfo> userInfoList = userInfoServiceImpl.getByMaster();
        System.out.println("当前数据源为: " + userInfoList.get(0).getName());
        return userInfoList;
    }

    @ApiOperation(value = "从库-读取数据")
    @RequestMapping(method = RequestMethod.GET, value = "/other")
    @ResponseBody
    public List<UserInfo> getByOther() {
        List<UserInfo> userInfoList = userInfoServiceImpl.getByOther();
        System.out.println("当前数据源为: " + userInfoList.get(0).getName());
        return userInfoList;
    }

    @ApiOperation(value = "yml-getByRead")
    @RequestMapping(method = RequestMethod.GET, value = "/getByRead")
    @ResponseBody
    public List<UserInfo> getByRead() {
        List<UserInfo> userInfoList = userInfoServiceImpl.getByRead();
        System.out.println("当前数据源为: " + userInfoList.get(0).getName());
        return userInfoList;
    }

    @ApiOperation(value = "yml-getByWrite")
    @RequestMapping(method = RequestMethod.GET, value = "/getByWrite")
    @ResponseBody
    public List<UserInfo> getByWrite() {
        List<UserInfo> userInfoList = userInfoServiceImpl.getByWrite();
        System.out.println("当前数据源为: " + userInfoList.get(0).getName());
        return userInfoList;
    }
}
