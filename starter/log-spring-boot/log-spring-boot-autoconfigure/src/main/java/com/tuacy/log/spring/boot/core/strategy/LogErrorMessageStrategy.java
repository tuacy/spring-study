package com.tuacy.log.spring.boot.core.strategy;


import com.tuacy.common.api.response.PageResponse;
import com.tuacy.common.api.response.Response;
import com.tuacy.common.api.response.ResponseErrorStatus;

/**
 * 获取错误对应的描述信息
 *
 * @name: LogErrorMessageStrategy
 * @author: tuacy.
 * @date: 2020/4/14.
 * @version: 1.0
 */
public abstract class LogErrorMessageStrategy {
    /**
     * 获取错误描述信息
     *
     * @param response 请求的返回结果
     * @return 错误描述信息
     */
    public abstract String errorMessage(Object response);


    /**
     * 获取业务请求结果对应的code
     */
    protected int getResponseStatusCode(Object response) {
        if (response == null) {
            return ResponseErrorStatus.SUCCESS.getCode();
        }
        if (response instanceof PageResponse) {
            PageResponse responseResultBiz = (PageResponse) response;
            return responseResultBiz.getCode();
        } else if (response instanceof Response) {
            Response responsePageResultBiz = (Response) response;
            return responsePageResultBiz.getCode();
        } else {
            return ResponseErrorStatus.UNKNOWN_ERROR.getCode();
        }
    }

    /**
     * 获取业务请求结果对应的message信息
     */
    protected String getResponseMessage(Object response) {
        if (response == null) {
            return null;
        }
        if (response instanceof PageResponse) {
            PageResponse responseResultBiz = (PageResponse) response;
            return responseResultBiz.getMsg();
        } else if (response instanceof Response) {
            Response responsePageResultBiz = (Response) response;
            return responsePageResultBiz.getMsg();
        } else {
            return null;
        }
    }

}
