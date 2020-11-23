package com.example.hello.entity;

/**
 * 流程参数
 *
 * @author zk
 * @date 2020/10/20 13:13
 */
public class HkdProcessParam {
    /**
     * userId
     */
    private String userId;
    /**
     * 业务员
     */
    private String salesman;
    /**
     * 业务主办
     */
    private String businessDirector;
    /**
     * 团队负责人
     */
    private String teamLeader;
    /**
     * 合规岗
     */
    private String compliancePost;
    /**
     * 资金经办人
     */
    private String fundManager;
    /**
     * 创建时间
     */
    private Long creationTime;
    /**
     * 是否通过:0-已填报,未审核;1-已审核;2-复审;3-被驳回
     */
    private Integer passed;
    /**
     * 批注信息
     */
    private String message;
    /**
     * 未勾选任务的processDefinitionKey,对应流程图里的id
     * 判断 业务主办key(id)-ywZbCheck,团队负责人key(id)-teamLeaderCheck,合规岗key(id)-hggCheck是否勾选,
     * 自动完成对应任务节点,其中id是在流程图里写死的
     */
    private String unselectedProDefKey;
    /**
     * 流程图里的任务节点id
     */
    private String taskDefinitionKey;

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBusinessDirector() {
        return businessDirector;
    }

    public void setBusinessDirector(String businessDirector) {
        this.businessDirector = businessDirector;
    }

    public String getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(String teamLeader) {
        this.teamLeader = teamLeader;
    }

    public String getCompliancePost() {
        return compliancePost;
    }

    public void setCompliancePost(String compliancePost) {
        this.compliancePost = compliancePost;
    }

    public String getFundManager() {
        return fundManager;
    }

    public void setFundManager(String fundManager) {
        this.fundManager = fundManager;
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    public Integer getPassed() {
        return passed;
    }

    public void setPassed(Integer passed) {
        this.passed = passed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUnselectedProDefKey() {
        return unselectedProDefKey;
    }

    public void setUnselectedProDefKey(String unselectedProDefKey) {
        this.unselectedProDefKey = unselectedProDefKey;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }
}
