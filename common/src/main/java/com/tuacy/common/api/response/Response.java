package com.tuacy.common.api.response;

import java.io.Serializable;

/**
 * 接口返回值的封装
 *
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 21:48
 */
public class Response<T> implements Serializable {
    private static final long serialVersionUID = -8970366016772121871L;

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

    public Response() {}

    public Response(ResponseErrorStatus errorStatus) {
        this.code = errorStatus.getCode();
        this.msg = errorStatus.getMessage();
    }

    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public Response<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public Response<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Response<T> setData(T data) {
        this.data = data;
        return this;
    }
}
