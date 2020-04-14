package com.tuacy.web.log.spring.boot.core;


import com.tuacy.common.entity.ApiResponse;
import com.tuacy.common.entity.ApiResponseErrorStatus;
import com.tuacy.common.entity.ApiResponsePage;

/**
 * 获取错误对应的描述信息
 *
 * @name: BizLogErrorMessageStrategy
 * @author: tuacy.
 * @date: 2020/4/14.
 * @version: 1.0
 */
public abstract class BizLogErrorMessageStrategy {
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
            return ApiResponseErrorStatus.SUCCESS.getCode();
        }
        if (response instanceof ApiResponsePage) {
            ApiResponsePage responseResultBiz = (ApiResponsePage) response;
            return responseResultBiz.getCode();
        } else if (response instanceof ApiResponse) {
            ApiResponse responsePageResultBiz = (ApiResponse) response;
            return responsePageResultBiz.getCode();
        } else {
            return ApiResponseErrorStatus.UNKNOWN_ERROR.getCode();
        }
    }

    /**
     * 获取业务请求结果对应的message信息
     */
    protected String getResponseMessage(Object response) {
        if (response == null) {
            return null;
        }
        if (response instanceof ApiResponsePage) {
            ApiResponsePage responseResultBiz = (ApiResponsePage) response;
            return responseResultBiz.getMsg();
        } else if (response instanceof ApiResponse) {
            ApiResponse responsePageResultBiz = (ApiResponse) response;
            return responsePageResultBiz.getMsg();
        } else {
            return null;
        }
    }

}
