package com.tuacy.common.entity;

import lombok.Data;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 21:59
 */
public enum ResultType {
    SUCCESS(0, "SUCCESS"),
    OTHER_ERROR(1001, "Other error");

    private int value;
    private String desc;

    ResultType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
