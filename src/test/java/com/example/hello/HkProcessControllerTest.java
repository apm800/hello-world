package com.example.hello;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.hello.entity.HkdProcessParam;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

/**
 * @author zk
 * @date 2020/11/20 16:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HkProcessControllerTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private Long creationTime;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void deploymentProcess() throws Exception {
        //删除相关流程
        deleteProcess();

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/hkProcessController/deploymentProcess")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
        //开启流程
        startProcess();
        //业务员完成填报
        queryAndCompleteTask();
        //相关人员审核
        check();
        //查看流程进度
        queryRateOfProgress();
        //查看流转记录
        queryTransferRecord();

        System.out.println(creationTime);
    }

    @Test
    public void startProcess() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/hkProcessController/startProcess")
                        .contentType(MediaType.APPLICATION_JSON_UTF8).content(makeParam()))
                .andDo(MockMvcResultHandlers.print()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = JSON.parseObject(content);
        creationTime = jsonObject.getJSONObject("result").getLong("creationTime");
        System.out.println(creationTime);
        System.out.println(content);
    }

    @Test
    public void queryRateOfProgress() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/hkProcessController/queryRateOfProgress")
                        .contentType(MediaType.APPLICATION_JSON_UTF8).content(makeParam()))
                .andDo(MockMvcResultHandlers.print()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void queryTransferRecord() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/hkProcessController/queryTransferRecord")
                        .contentType(MediaType.APPLICATION_JSON_UTF8).content(makeParam()))
                .andDo(MockMvcResultHandlers.print()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void queryAndCompleteTask() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/hkProcessController/queryAndCompleteTask")
                        .contentType(MediaType.APPLICATION_JSON_UTF8).content(makeParam()))
                .andDo(MockMvcResultHandlers.print()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void check() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/hkProcessController/check")
                        .contentType(MediaType.APPLICATION_JSON_UTF8).content(makeParam()))
                .andDo(MockMvcResultHandlers.print()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void deleteProcessInstance() throws Exception {
        HashMap<String, String> map = new HashMap<>(2);
        map.put("salesman", "业务员xx");
        map.put("creationTime", "");
        String jsonString = JSONObject.toJSONString(map);
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/hkProcessController/deleteProcessInstance")
                        .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonString))
                .andDo(MockMvcResultHandlers.print()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void deleteProcess() throws Exception {
        HashMap<String, String> map = new HashMap<>(2);
        map.put("definitionKey", "hkdProcess");
        map.put("definitionId", "");
        String jsonString = JSONObject.toJSONString(map);
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/hkProcessController/deleteProcess")
                        .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonString))
                .andDo(MockMvcResultHandlers.print()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
    }

    private String makeParam() {
        HkdProcessParam processParam = new HkdProcessParam();

        processParam.setBusinessDirector("主管xx");
        processParam.setCompliancePost("合规岗xx");
        //创建时间可以通过查询流程进度查看
        processParam.setCreationTime(creationTime);
        processParam.setFundManager("资金经办人xx");
        processParam.setMessage("");
        //0-已填报,未审核;1-已审核;2-复审;3-被驳回
        processParam.setPassed(1);
        processParam.setSalesman("业务员xx");
        //ywZbCheck teamLeaderCheck hggCheck fundManagerCheck 3
        processParam.setTaskDefinitionKey("ywZbCheck");
        processParam.setTeamLeader("团队领导xx");
        processParam.setUnselectedProDefKey("");
        processParam.setUserId("");

        return JSONObject.toJSONString(processParam);
    }
}
