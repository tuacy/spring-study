package com.tuacy.common.utils;

import com.tuacy.common.api.response.ResponseErrorStatus;
import com.tuacy.common.api.response.Response;
import com.tuacy.common.exception.ResponseException;

/**
 * 返回结果工具类 -- 将结果转换为封装后的对象
 *
 * @name: ResponseResultUtil
 * @author: tuacy.
 * @date: 2020/3/23.
 * @version: 1.0
 */
public class ResponseResultUtil {

    private ResponseResultUtil() {
    }

    public static <T> Response<T> ok() {
        return new Response<>(ResponseErrorStatus.SUCCESS);
    }

    public static <T> Response<T> ok(T data) {
        return new Response<>(ResponseErrorStatus.SUCCESS.getCode(), ResponseErrorStatus.SUCCESS.getMessage(), data);
    }

    public static <T> Response<T> error() {
        return new Response<>(ResponseErrorStatus.UNKNOWN_ERROR);
    }

    /**
     * 400错误，bad request
     */
    public static <T> Response<T> error400() {
        return new Response<>(ResponseErrorStatus.ERROR_400);
    }

    /**
     * 401错误，未授权
     */
    public static <T> Response<T> error401() {
        return new Response<>(ResponseErrorStatus.ERROR_401);
    }

    /**
     * 403错误，权限不足
     */
    public static <T> Response<T> error403() {
        return new Response<>(ResponseErrorStatus.ERROR_403);
    }

    /**
     * 404错误，处理器不存在异常，资源不存在异常
     */
    public static <T> Response<T> error404() {
        return new Response<>(ResponseErrorStatus.ERROR_404);
    }

    /**
     * 500异常,服务器内部错误
     */
    public static <T> Response<T> error500() {
        return new Response<>(ResponseErrorStatus.ERROR_500);
    }

    /**
     * 参数异常
     */
    public static <T> Response<T> errorParam() {
        return new Response<>(ResponseErrorStatus.ERROR_PARAM);
    }

    public static <T> Response<T> error(ResponseException responseException) {
        return new Response<>(responseException.getErrorStatus());
    }

    public static <T> Response<T> error(int code, String message) {
        return new Response<>(code, message);
    }

}
