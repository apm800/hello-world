package com.example.hello.entity;

import javafx.scene.control.CheckBox;

/**
 * @author zhoukai
 * @date 2020/8/6 13:41
 */
public class CxCadreInfo {
    public CheckBox cb = new CheckBox();
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
