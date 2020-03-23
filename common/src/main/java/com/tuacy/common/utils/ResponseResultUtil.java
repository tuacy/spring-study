package com.tuacy.common.utils;

import com.tuacy.common.entity.ResponseErrorStatus;
import com.tuacy.common.entity.ResponseResult;

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
        return new ResponseResult<>(ResponseErrorStatus.OK);
    }

    public static <T> ResponseResult<T> ok(T data) {
        return new ResponseResult<>(ResponseErrorStatus.OK.getCode(), ResponseErrorStatus.OK.getMessage(), data);
    }

}
