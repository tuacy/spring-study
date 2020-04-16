package com.tuacy.log.spring.boot.core.strategy;

/**
 * 判断请求是否成功
 * @name: LogSuccessCheckStrategy
 * @author: tuacy.
 * @date: 2020/4/14.
 * @version: 1.0
 */
public abstract class LogSuccessCheckStrategy {
    /**
     * 判断请求是否执行成功
     * @param response 请求的返回结果
     * @return 是否成功
     */
    public abstract boolean isSuccess(Object response);

}
