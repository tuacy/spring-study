package com.tuacy.common.entity;

import com.tuacy.common.entity.response.ApiPageResponse;
import com.tuacy.common.entity.response.ApiResponse;
import com.tuacy.common.utils.ResponseResultUtil;

import java.util.List;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 21:47
 */
public abstract class ApiBaseController {

    protected <T> ApiResponse<T> setResult() {
        return ResponseResultUtil.ok();
    }

    protected <T> ApiResponse<T> setResult(T data) {
        if (data != null) {
            return ResponseResultUtil.ok(data);
        }
        return ResponseResultUtil.ok();
    }

    protected <T> ApiPageResponse<List<T>> setPageResult(PageEntity<T> data) {
        if (data != null) {
            ApiPageResponse<List<T>> pageResponse = new ApiPageResponse<>(ResponseResultUtil.ok(data.getData()));
            pageResponse.setItemCount(data.getItemCount());
            pageResponse.setPageCount(data.getPageCount());
            pageResponse.setPageIndex(data.getPageNo());
            return pageResponse;
        }
        return new ApiPageResponse<>(ResponseResultUtil.ok());
    }

}
