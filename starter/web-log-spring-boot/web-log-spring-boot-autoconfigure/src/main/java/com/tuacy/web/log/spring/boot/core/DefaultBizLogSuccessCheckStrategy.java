package com.tuacy.web.log.spring.boot.core;

import com.tuacy.common.entity.ApiResponse;
import com.tuacy.common.entity.ApiResponseErrorStatus;
import com.tuacy.common.entity.ApiResponsePage;
import lombok.extern.slf4j.Slf4j;

/**
 * @name: DefaultBizLogContentStrategy
 * @author: tuacy.
 * @date: 2020/4/13.
 * @version: 1.0
 * @Description:
 */
@Slf4j
public class DefaultBizLogSuccessCheckStrategy extends BizLogSuccessCheckStrategy {

    /**
     * 判断请求是否执行成功
     *
     * @param response 请求的返回结果
     * @return 是否成功
     */
    @Override
    public boolean isSuccess(Object response) {
        if (response == null) {
            return true;
        }
        if (response instanceof ApiResponsePage) {
            ApiResponsePage resultBiz = (ApiResponsePage) response;
            return resultBiz.getCode() ==  ApiResponseErrorStatus.SUCCESS.getCode();
        } else if (response instanceof ApiResponse) {
            ApiResponse resultConvert = (ApiResponse) response;
            return resultConvert.getCode() ==  ApiResponseErrorStatus.SUCCESS.getCode();
        } else {
            return true;
        }
    }
}
