package com.tuacy.common.utils;

import com.tuacy.common.entity.ResponseErrorStatus;
import com.tuacy.common.entity.ResponseResult;
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

    public static <T> ResponseResult<T> ok() {
        return new ResponseResult<>(ResponseErrorStatus.SUCCESS);
    }

    public static <T> ResponseResult<T> ok(T data) {
        return new ResponseResult<>(ResponseErrorStatus.SUCCESS.getCode(), ResponseErrorStatus.SUCCESS.getMessage(), data);
    }

    public static <T> ResponseResult<T> eroor() {
        return new ResponseResult<>(ResponseErrorStatus.UNKNOWN_ERROR);
    }

    public static <T> ResponseResult<T> error404() {
        return new ResponseResult<>(ResponseErrorStatus.ERROR_404);
    }

    public static <T> ResponseResult<T> error500() {
        return new ResponseResult<>(ResponseErrorStatus.ERROR_500);
    }

    public static <T> ResponseResult<T> eroor(ResponseException responseException) {
        return new ResponseResult<>(responseException.getErrorStatus());
    }

    public static <T> ResponseResult<T> eroor(int code, String message) {
        return new ResponseResult<>(code, message);
    }

}
