package com.example.hello.entity;

/**
 * @author zhoukai
 * @date 2020/8/20 11:05
 */
public class EtfEntity {
    private Integer etfSequenceNum;
    private String etfStatus;
    private String admin;
    private String etfCode;
    private String etfName;
    private String cilType;
    private String cilSource;

    public Integer getEtfSequenceNum() {
        return etfSequenceNum;
    }

    public void setEtfSequenceNum(Integer etfSequenceNum) {
        this.etfSequenceNum = etfSequenceNum;
    }

    public String getEtfStatus() {
        return etfStatus;
    }

    public void setEtfStatus(String etfStatus) {
        this.etfStatus = etfStatus;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getEtfCode() {
        return etfCode;
    }

    public void setEtfCode(String etfCode) {
        this.etfCode = etfCode;
    }

    public String getEtfName() {
        return etfName;
    }

    public void setEtfName(String etfName) {
        this.etfName = etfName;
    }

    public String getCilType() {
        return cilType;
    }

    public void setCilType(String cilType) {
        this.cilType = cilType;
    }

    public String getCilSource() {
        return cilSource;
    }

    public void setCilSource(String cilSource) {
        this.cilSource = cilSource;
    }
}
