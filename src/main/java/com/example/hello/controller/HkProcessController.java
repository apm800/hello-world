package com.example.hello.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.hello.commons.R;
import com.example.hello.entity.HkdProcessParam;
import com.example.hello.service.HkProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author zhoukai
 * @date 2020/5/28 10:55
 */
@Api(value = "hkProcessController", tags = "划款流程-服务接口")
@RestController
@RequestMapping(value = "hkProcessController")
public class HkProcessController {
    @Autowired
    private HkProcessService hkProcessService;

    @ApiOperation(value = "部署流程")
    @RequestMapping(method = RequestMethod.POST, value = "/deploymentProcess")
    public void deploymentProcess() {
        hkProcessService.deploymentProcess();
    }

    @ApiOperation(value = "开启流程")
    @RequestMapping(headers = "Content-Type=application/json;charset=UTF-8", method = RequestMethod.POST, value = "/startProcess")
    public R startProcess(@RequestBody HkdProcessParam param) {
        return R.ok().put("result", hkProcessService.startProcess(param));
    }

    @ApiOperation(value = "查看流程进度")
    @RequestMapping(headers = "Content-Type=application/json;charset=UTF-8", method = RequestMethod.POST, value = "/queryRateOfProgress")
    public R queryRateOfProgress(@RequestBody HkdProcessParam param) {
        List<JSONObject> jsonList = hkProcessService.queryRateOfProgress(param);
        return R.ok().put("result", jsonList);
    }

    @ApiOperation(value = "查看流转记录")
    @RequestMapping(headers = "Content-Type=application/json;charset=UTF-8", method = RequestMethod.POST, value = "/queryTransferRecord")
    public R queryTransferRecord(@RequestBody HkdProcessParam param) {
        List<Map<String, String>> resultList = hkProcessService.queryTransferRecord(param);
        return R.ok().put("result", resultList);
    }

    @ApiOperation(value = "业务员完成填报任务")
    @RequestMapping(headers = "Content-Type=application/json;charset=UTF-8", method = RequestMethod.POST, value = "/queryAndCompleteTask")
    public R queryAndCompleteTask(@RequestBody HkdProcessParam param) {
        hkProcessService.queryAndCompleteTask(param);
        return R.ok();
    }

    @ApiOperation(value = "相关人员审核")
    @RequestMapping(headers = "Content-Type=application/json;charset=UTF-8", method = RequestMethod.POST, value = "/check")
    public R check(@RequestBody HkdProcessParam param) {
        hkProcessService.check(param);
        return R.ok();
    }

    @ApiOperation(value = "删除流程实例")
    @RequestMapping(headers = "Content-Type=application/json;charset=UTF-8", method = RequestMethod.POST, value = "/deleteProcessInstance")
    public R deleteProcessInstance(@RequestBody Map<String, String> param) {
        hkProcessService.deleteProcessInstance(param);
        return R.ok();
    }

    @ApiOperation(value = "删除流程,谨慎操作!")
    @RequestMapping(headers = "Content-Type=application/json;charset=UTF-8", method = RequestMethod.POST, value = "/deleteProcess")
    public R deleteProcess(@RequestBody Map<String, String> param) {
        hkProcessService.deleteProcess(param);
        return R.ok();
    }
}
