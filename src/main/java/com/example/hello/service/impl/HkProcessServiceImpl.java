package com.example.hello.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.hello.entity.HkdProcessParam;
import com.example.hello.service.HkProcessService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author zhoukai
 * @date 2020/6/5 9:44
 */
@Service
public class HkProcessServiceImpl implements HkProcessService {

    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RepositoryService repositoryService;

    private static final Logger LOGGER = LogManager.getLogger(HkProcessServiceImpl.class);
    /**
     * act_re_procdef中的key
     */
    private static final String PROCESS_KEY = "hkdProcess";
    /**
     * 业务员
     */
    private static final String SALESMAN = "salesman";
    /**
     * 创建时间
     */
    private static final String CREATION_TIME = "creationTime";
    /**
     * 参数
     */
    private static final String PARAMS = "params";
    /**
     * 信息
     */
    private static final String MESSAGE = "message";
    /**
     * 业务主办
     */
    private static String businessDirector = "businessDirector";
    /**
     * 团队负责人
     */
    private static String teamLeader = "teamLeader";
    /**
     * 合规岗
     */
    private static String compliancePost = "compliancePost";
    /**
     * 资金经办人
     */
    private static String fundManager = "fundManager";


    @Override
    public void deploymentProcess() {
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                //对应deployment表中的name
                .name("划款流程")
                .addClasspathResource("process/hkdProcess.bpmn")
                .deploy();
        LOGGER.info("部署流程SUCCESS,流程id:{},流程name:{}", deployment.getId(), deployment.getName());
    }

    @Override
    public JSONObject startProcess(HkdProcessParam hkdProcessParam) {
        hkdProcessParam.setCreationTime(Long.parseLong(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")));
        JSONObject jsonObject = (JSONObject) JSON.toJSON(hkdProcessParam);
        //将信息加入map,以便传入流程中
        Map<String, Object> variables = new HashMap<>(2);
        //对应流程图中assignee的参数${salesman}
        variables.put(SALESMAN, hkdProcessParam.getSalesman());
        //开启流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_KEY, variables);
        //得到的实例流程id
        String processInstanceId = processInstance.getId();
        Task task = taskService.createTaskQuery()
                //根据实例流程id查询
                .processInstanceId(processInstanceId).singleResult();
        jsonObject.put("processInstanceId", processInstanceId);
        taskService.setVariable(task.getId(), PARAMS, jsonObject);
        LOGGER.info("流程开启成功.......实例流程id:" + processInstanceId);
        LOGGER.info("params:{}", jsonObject);
        System.out.println(hkdProcessParam.getCreationTime());
        return jsonObject;
    }

    @Override
    public List<JSONObject> queryRateOfProgress(HkdProcessParam param) {

        String salesman = param.getSalesman();
        Long creationTime = param.getCreationTime();

        ArrayList<JSONObject> resultList = new ArrayList<>();
        List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().processDefinitionKey(PROCESS_KEY).list();
        String status = "end";
        JSONObject alreadyEnd = new JSONObject();
        alreadyEnd.put("position", status);
        alreadyEnd.put("message", "");
        //划款流程都已结束
        if (processInstanceList.size() == 0) {
            resultList.add(alreadyEnd);
            return resultList;
        } else {
            List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(PROCESS_KEY).list();
            for (Task task : taskList) {
                JSONObject jsonEntity = runtimeService.getVariable(task.getExecutionId(), PARAMS, JSONObject.class);
                System.out.println(jsonEntity.getLong(CREATION_TIME));
                //根据业务场景,可以把processInstanceId存到数据库,用于唯一性判断和相关操作
                if (match(salesman, jsonEntity, creationTime)) {

                    for (ProcessInstance instance : processInstanceList) {
                        if (task.getProcessInstanceId().equals(instance.getId())) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("position", task.getName());
                            jsonObject.put("message", jsonEntity.getString("message"));
                            resultList.add(jsonObject);
                        }
                    }
                }
            }

            if (resultList.size() > 0) {
                return resultList;
            }

            List<HistoricVariableInstance> variableInstanceList = historyService.createHistoricVariableInstanceQuery().list();
            for (HistoricVariableInstance variableInstance : variableInstanceList) {
                if (PARAMS.equals(variableInstance.getVariableName())) {
                    Object value = variableInstance.getValue();
                    JSONObject jsonEntity = (JSONObject) JSON.toJSON(value);
                    //根据业务场景,可以把processInstanceId存到数据库,用于唯一性判断和相关操作
                    if (match(salesman, jsonEntity, creationTime)) {
                        resultList.add(alreadyEnd);
                        return resultList;
                    }
                }
            }
        }

        return resultList;
    }

    @Override
    public List<Map<String, String>> queryTransferRecord(HkdProcessParam param) {

        String salesman = param.getSalesman();
        Long creationTime = param.getCreationTime();

        List<HistoricVariableInstance> list = processEngine.getHistoryService()
                .createHistoricVariableInstanceQuery().list();

        String processInstanceId = "";
        for (HistoricVariableInstance hvi : list) {
            if (PARAMS.equals(hvi.getVariableName())) {
                Object value = hvi.getValue();
                JSONObject jsonEntity = (JSONObject) JSON.toJSON(value);
                //根据业务场景,可以把processInstanceId存到数据库,用于唯一性判断和相关操作
                if (match(salesman, jsonEntity, creationTime)) {
                    processInstanceId = hvi.getProcessInstanceId();

                }
            }
        }
        List<HistoricVariableInstance> variableList = processEngine.getHistoryService()
                .createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();

        List<HistoricActivityInstance> activityList = processEngine.getHistoryService()
                .createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();

        activityList.sort(Comparator.comparing(HistoricActivityInstance::getEndTime, Comparator.nullsLast(Date::compareTo)));

        List<Map<String, String>> resultList = new ArrayList<>();
        for (HistoricActivityInstance hai : activityList) {
            String activityName = hai.getActivityName();
            if ("sequenceFlow".equals(hai.getActivityType())) {
                continue;
            }
            if ("ExclusiveGateway".equals(activityName)) {
                continue;
            }
            if ("ParallelGateway".equals(activityName)) {
                continue;
            }
            if ("StartEvent".equals(activityName)) {
                activityName = "流程开始";
            }
            if ("EndEvent".equals(activityName)) {
                activityName = "流程结束";
            }
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("activityName", activityName);
            resultMap.put("assignee", hai.getAssignee() == null ? "" : hai.getAssignee());
            resultMap.put("startTime", DateFormatUtils.format(hai.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
            resultMap.put("endTime", hai.getEndTime() == null ? "" : DateFormatUtils.format(hai.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
            resultMap.put("message", "");
            for (HistoricVariableInstance hvi : variableList) {
                if (hai.getId().equals(hvi.getVariableName())) {
                    resultMap.replace("message", hvi.getValue().toString());
                }
            }
            resultList.add(resultMap);
        }
        return resultList;
    }

    /**
     * 查出任务并完成
     *
     * @param processParam
     * @author zhoukai
     * @date 2020/5/28 16:05
     */
    @Override
    public void queryAndCompleteTask(HkdProcessParam processParam) {
        String salesman = processParam.getSalesman();
        List<Task> taskList = taskService.createTaskQuery()
                //根据流程key值查询
                .processDefinitionKey(PROCESS_KEY)
                //根据assignee查询
                .taskAssignee(salesman)
                .list();

        for (Task task : taskList) {
            JSONObject jsonEntity = runtimeService.getVariable(task.getExecutionId(), PARAMS, JSONObject.class);
            //根据业务场景,可以把processInstanceId存到数据库,用于唯一性判断和相关操作
            if (match(salesman, jsonEntity, processParam.getCreationTime())) {
                taskService.complete(task.getId(), JSONObject.toJavaObject(jsonEntity, Map.class));
                LOGGER.info("完成[" + task.getName() + "]节点任务,params:{}", jsonEntity);

                if (StringUtils.isBlank(jsonEntity.getString("businessDirector"))) {
                    processParam.setUnselectedProDefKey("ywZbCheck");
                    processParam.setPassed(1);
                    check(processParam);
                }
                if (StringUtils.isBlank(jsonEntity.getString("teamLeader"))) {
                    processParam.setUnselectedProDefKey("teamLeaderCheck");
                    processParam.setPassed(1);
                    check(processParam);
                }
                if (StringUtils.isBlank(jsonEntity.getString("compliancePost"))) {
                    processParam.setUnselectedProDefKey("hggCheck");
                    processParam.setPassed(1);
                    check(processParam);
                }
            }
        }
    }

    @Override
    public void check(HkdProcessParam param) {
        String message = param.getMessage();
        Integer passed = param.getPassed();
        String unselectedProDefKey = param.getUnselectedProDefKey();
        String taskDefinitionKey = param.getTaskDefinitionKey();

        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(PROCESS_KEY).list();

        Map<String, Object> variables = new HashMap<>(2);
        Map<String, String> dataMap = new HashMap<>(2);

        //0-已填报,未审核;1-已审核;2-复审;3-被驳回
        int reject = 3;
        if (reject == passed) {
            variables.put("result", 0);
            dataMap.put("message", message);
        } else {
            variables.put("result", 1);
        }
        for (Task task : taskList) {
            JSONObject jsonEntity = runtimeService.getVariable(task.getExecutionId(), PARAMS, JSONObject.class);
            //根据业务场景,可以把processInstanceId存到数据库,用于唯一性判断和相关操作
            if (match(param.getSalesman(), jsonEntity, param.getCreationTime())) {
                //unselectedProDefKey不是空,说明此节点没有勾选,直接完成
                if (StringUtils.isNotBlank(unselectedProDefKey) && task.getTaskDefinitionKey().equals(unselectedProDefKey)) {
                    taskService.complete(task.getId(), variables);
                    break;
                } else {
                    if (taskDefinitionKey.equals(task.getTaskDefinitionKey())) {
                        String processInstanceId = task.getProcessInstanceId();
                        String assignee = task.getAssignee();
                        dataMap.put("assignee", assignee);
                        variables.put("data", dataMap);

                        if (reject == passed && StringUtils.isNotBlank(message)) {
                            jsonEntity.put("message", message);
                            List<HistoricActivityInstance> activityList = processEngine.getHistoryService().createHistoricActivityInstanceQuery()
                                    .activityId(taskDefinitionKey).processInstanceId(processInstanceId).list();
                            for (HistoricActivityInstance hai : activityList) {
                                if (task.getExecutionId().equals(hai.getExecutionId())) {
                                    taskService.setVariable(task.getId(), hai.getId(), message);
                                }
                            }
                        } else {
                            Map<String, Object> taskVariables = taskService.getVariables(task.getId());
                            Map<String, String> map = (Map<String, String>) taskVariables.get("data");
                            if (null != map && assignee.equals(map.get("assignee"))) {
                                jsonEntity.put("message", "");
                            }
                        }
                        taskService.setVariable(task.getId(), PARAMS, jsonEntity);
                        if (passed == reject && !"fundManagerCheck".equals(task.getTaskDefinitionKey())) {
                            backToStep(jsonEntity.getString("processInstanceId"));
                            return;
                        } else {
                            taskService.complete(task.getId(), variables);
                        }
                        LOGGER.info("完成[" + task.getName() + "]节点任务,entity:{}", jsonEntity);
                    }
                }
            }
        }
    }

    @Override
    public void deleteProcessInstance(Object object) {
        JSONObject jsonObject = (JSONObject) JSON.toJSON(object);
        List<Task> taskList = taskService
                .createTaskQuery()
                //根据流程key值查询
                .processDefinitionKey(PROCESS_KEY).list();

        for (Task task : taskList) {
            JSONObject jsonEntity = runtimeService.getVariable(task.getExecutionId(), PARAMS, JSONObject.class);
            String salesman = jsonEntity.getString(SALESMAN);
            long creationTime = jsonEntity.getLong(CREATION_TIME);

            if (jsonObject.getString(SALESMAN).equals(salesman)
                    && jsonObject.getLong(CREATION_TIME).equals(creationTime)) {
                runtimeService.deleteProcessInstance(task.getProcessInstanceId(), creationTime + "-" + salesman + " 删除,同时删除相关任务节点");
                LOGGER.info("删除流程实例SUCCESS,entity:{}", jsonEntity);
            }
        }
    }

    @Override
    public void deleteProcess(Map<String, String> map) {
        String definitionKey = map.get("definitionKey");
        String definitionId = map.get("definitionId");

        ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();
        if (StringUtils.isNotBlank(definitionId)) {
            ProcessDefinition pd = query.processDefinitionKey(definitionKey).processDefinitionId(definitionId).singleResult();
            processEngine.getRepositoryService().deleteDeployment(pd.getDeploymentId(), true);
        } else {
            List<ProcessDefinition> pdList = query.processDefinitionKey(definitionKey).list();
            for (ProcessDefinition pd : pdList) {
                processEngine.getRepositoryService().deleteDeployment(pd.getDeploymentId(), true);
            }
        }
    }

    /**
     * 根据业务场景,可以把processInstanceId存到数据库,用于唯一性判断和相关操作
     *
     * @param salesman     业务员
     * @param jsonObject   参数
     * @param creationTime 创建时间
     * @author zk
     * @date 2020/12/9 15:25
     */
    private boolean match(String salesman, JSONObject jsonObject, Long creationTime) {
        return salesman.equals(jsonObject.getString(SALESMAN)) && creationTime.equals(jsonObject.getLong(CREATION_TIME));
    }

    private void backToStep(String processInstanceId) {
        // 并行网关的退回
        List<String> currentExecutionIds = new ArrayList<>();
        List<Execution> executions = runtimeService.createExecutionQuery().parentId(processInstanceId).list();
        executions.forEach(en -> currentExecutionIds.add(en.getId()));
        runtimeService.createChangeActivityStateBuilder()
                .moveExecutionsToSingleActivityId(currentExecutionIds, "salesmanFillIn")
                .changeState();
    }
}
