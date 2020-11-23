package com.example.hello.service;

import com.alibaba.fastjson.JSONObject;
import com.example.hello.entity.HkdProcessParam;

import java.util.List;
import java.util.Map;

/**
 * @author zhoukai
 * @date 2020/6/5 9:44
 */
public interface HkProcessService {
    /**
     * 部署流程
     *
     * @author zk
     * @date 2020/10/14 13:24
     */
    void deploymentProcess();

    /**
     * 开启流程
     *
     * @param param 划款流程参数
     * @return JSONObject
     * @author zk
     * @date 2020/10/14 13:24
     */
    JSONObject startProcess(HkdProcessParam param);

    /**
     * 查看流程进度
     *
     * @param param 划款流程参数
     * @return List<JSONObject>
     * @author zk
     * @date 2020/10/14 13:25
     */
    List<JSONObject> queryRateOfProgress(HkdProcessParam param);

    /**
     * 查看流转记录
     *
     * @param param 划款流程参数
     * @return List<Map < String, String>>
     * @author zk
     * @date 2020/10/14 13:23
     */
    List<Map<String, String>> queryTransferRecord(HkdProcessParam param);

    /**
     * 业务员完成填报任务
     *
     * @param param 划款流程参数
     * @author zk
     * @date 2020/10/14 13:26
     */
    void queryAndCompleteTask(HkdProcessParam param);

    /**
     * 相关人员审核
     *
     * @param param 划款流程参数
     * @author zk
     * @date 2020/10/14 13:25
     */
    void check(HkdProcessParam param);

    /**
     * 删除流程实例
     *
     * @param object 流程实例
     * @author zhoukai
     * @date 2020/5/28 16:31
     */
    void deleteProcessInstance(Object object);

    /**
     * 删除整个流程
     *
     * @param param definitionKey-act_re_procdef中的key和definitionId-act_re_procdef中的id
     * @author zk
     * @date 2020/10/14 13:21
     */
    void deleteProcess(Map<String, String> param);
}
