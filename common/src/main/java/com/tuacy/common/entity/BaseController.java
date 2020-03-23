package com.tuacy.common.entity;

import com.tuacy.common.utils.ResponseResultUtil;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 21:47
 */
public abstract class BaseController {


    public <T> ResponseResult<T> setResult(T data) {
        return ResponseResultUtil.ok(data);
    }


}
