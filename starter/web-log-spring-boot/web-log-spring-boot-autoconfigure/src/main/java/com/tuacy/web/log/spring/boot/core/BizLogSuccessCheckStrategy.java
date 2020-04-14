package com.tuacy.web.log.spring.boot.core;

/**
 * 判断请求是否成功
 * @name: BizLogSuccessCheckStrategy
 * @author: tuacy.
 * @date: 2020/4/14.
 * @version: 1.0
 */
public abstract class BizLogSuccessCheckStrategy {
    /**
     * 判断请求是否执行成功
     * @param response 请求的返回结果
     * @return 是否成功
     */
    public abstract boolean isSuccess(Object response);

}
