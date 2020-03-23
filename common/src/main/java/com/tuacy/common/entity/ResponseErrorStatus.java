package com.tuacy.common.entity;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 21:59
 */
public enum ResponseErrorStatus {
    OK(0, "OK"),
    OTHER_ERROR(1001, "Other error");

    private int code;
    private String message;

    ResponseErrorStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
