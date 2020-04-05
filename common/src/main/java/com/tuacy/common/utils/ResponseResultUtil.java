package com.tuacy.common.utils;

import com.tuacy.common.entity.response.ApiResponseErrorStatus;
import com.tuacy.common.entity.response.ApiResponse;
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

    public static <T> ApiResponse<T> ok() {
        return new ApiResponse<>(ApiResponseErrorStatus.SUCCESS);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(ApiResponseErrorStatus.SUCCESS.getCode(), ApiResponseErrorStatus.SUCCESS.getMessage(), data);
    }

    public static <T> ApiResponse<T> error() {
        return new ApiResponse<>(ApiResponseErrorStatus.UNKNOWN_ERROR);
    }

    /**
     * 400错误，bad request
     */
    public static <T> ApiResponse<T> error400() {
        return new ApiResponse<>(ApiResponseErrorStatus.ERROR_400);
    }

    /**
     * 401错误，未授权
     */
    public static <T> ApiResponse<T> error401() {
        return new ApiResponse<>(ApiResponseErrorStatus.ERROR_401);
    }

    /**
     * 403错误，权限不足
     */
    public static <T> ApiResponse<T> error403() {
        return new ApiResponse<>(ApiResponseErrorStatus.ERROR_403);
    }

    /**
     * 404错误，处理器不存在异常，资源不存在异常
     */
    public static <T> ApiResponse<T> error404() {
        return new ApiResponse<>(ApiResponseErrorStatus.ERROR_404);
    }

    /**
     * 500异常,服务器内部错误
     */
    public static <T> ApiResponse<T> error500() {
        return new ApiResponse<>(ApiResponseErrorStatus.ERROR_500);
    }

    public static <T> ApiResponse<T> error(ResponseException responseException) {
        return new ApiResponse<>(responseException.getErrorStatus());
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code, message);
    }

}
