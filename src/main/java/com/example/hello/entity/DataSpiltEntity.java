package com.example.hello.entity;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author zk
 * @date 2020/9/15 14:03
 */
public class DataSpiltEntity extends HashMap implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    private static DataSpiltEntity dataSpiltEntity = new DataSpiltEntity();

    public DataSpiltEntity() {
        super();
    }

    /**
     * 调用对象创建优化
     *
     * @return
     */
    public static DataSpiltEntity getInstance() {
        try {
            return (DataSpiltEntity) dataSpiltEntity.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DataSpiltEntity();
    }
}
