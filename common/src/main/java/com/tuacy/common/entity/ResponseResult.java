package com.tuacy.common.entity;

import lombok.Data;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 21:48
 */
@Data
public class ResponseResult<T> {

    private int code;
    private String message;
    private T data;

    public ResponseResult(ResponseErrorStatus errorStatus) {
        this.code = errorStatus.getCode();
        this.message = errorStatus.getMessage();
    }

    public ResponseResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
