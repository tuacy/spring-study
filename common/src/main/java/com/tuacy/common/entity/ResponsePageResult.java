package com.tuacy.common.entity;

import lombok.Data;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 21:56
 */
@Data
public class ResponsePageResult<T> extends ResponseResult<T> {

    private int itemCount;
    private int pageIndex;
    private int pageCount;

    public ResponsePageResult(ResponseErrorStatus errorStatus, int itemCount) {
        super(errorStatus);
        this.itemCount = itemCount;
    }

    public ResponsePageResult(int code, String message, T data, int itemCount) {
        super(code, message, data);
        this.itemCount = itemCount;
    }
}
