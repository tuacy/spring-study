package com.tuacy.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口返回值的封装
 *
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 21:48
 */
@Data
public class ResponseResult<T> implements Serializable {

    /**
     * 状态值
     */
    private int code;
    /**
     * 状态消息
     */
    private String msg;
    /**
     * 结果
     */
    private T data;

    public ResponseResult(ResponseErrorStatus errorStatus) {
        this.code = errorStatus.getCode();
        this.msg = errorStatus.getMessage();
    }

    public ResponseResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
