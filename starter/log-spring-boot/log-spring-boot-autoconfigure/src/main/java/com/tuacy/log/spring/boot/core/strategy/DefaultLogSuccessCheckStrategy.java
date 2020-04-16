package com.tuacy.log.spring.boot.core.strategy;

import com.tuacy.common.api.response.PageResponse;
import com.tuacy.common.api.response.Response;
import com.tuacy.common.api.response.ResponseErrorStatus;
import lombok.extern.slf4j.Slf4j;

/**
 * @name: DefaultLogContentStrategy
 * @author: tuacy.
 * @date: 2020/4/13.
 * @version: 1.0
 * @Description:
 */
@Slf4j
public class DefaultLogSuccessCheckStrategy extends LogSuccessCheckStrategy {

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
        if (response instanceof PageResponse) {
            PageResponse resultBiz = (PageResponse) response;
            return resultBiz.getCode() == ResponseErrorStatus.SUCCESS.getCode();
        } else if (response instanceof Response) {
            Response resultConvert = (Response) response;
            return resultConvert.getCode() == ResponseErrorStatus.SUCCESS.getCode();
        } else {
            return true;
        }
    }
}
