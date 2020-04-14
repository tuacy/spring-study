package com.tuacy.web.log.spring.boot.core;

import lombok.extern.slf4j.Slf4j;

/**
 * @name: DefaultBizLogErrorMessageStrategy
 * @author: tuacy.
 * @date: 2020/4/13.
 * @version: 1.0
 * @Description:
 */
@Slf4j
public class DefaultBizLogErrorMessageStrategy extends BizLogErrorMessageStrategy {

    /**
     * 获取错误描述信息
     *
     * @param response 请求的返回结果
     * @return 错误描述信息
     */
    @Override
    public String errorMessage(Object response) {
        return getResponseMessage(response);
    }
}
