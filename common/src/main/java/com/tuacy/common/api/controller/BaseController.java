package com.tuacy.common.api.controller;

import com.tuacy.common.api.response.PageResponse;
import com.tuacy.common.api.response.Response;
import com.tuacy.common.entity.PageEntity;
import com.tuacy.common.utils.ResponseResultUtil;

import java.util.List;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 21:47
 */
public abstract class BaseController {

    protected <T> Response<T> setResult() {
        return ResponseResultUtil.ok();
    }

    protected <T> Response<T> setResult(T data) {
        if (data != null) {
            return ResponseResultUtil.ok(data);
        }
        return ResponseResultUtil.ok();
    }

    protected <T> PageResponse<List<T>> setPageResult(PageEntity<T> data) {
        if (data != null) {
            PageResponse<List<T>> pageResponse = new PageResponse<>(ResponseResultUtil.ok(data.getData()));
            pageResponse.setItemCount(data.getItemCount());
            pageResponse.setPageCount(data.getPageCount());
            pageResponse.setPageIndex(data.getPageNo());
            return pageResponse;
        }
        return new PageResponse<>(ResponseResultUtil.ok());
    }

}
