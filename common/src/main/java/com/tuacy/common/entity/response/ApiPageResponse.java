package com.tuacy.common.entity.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 21:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApiPageResponse<T> extends ApiResponse<T> {

    private static final long serialVersionUID = -5587649148482038762L;

    private int itemCount;
    private int pageIndex;
    private int pageCount;

    public ApiPageResponse(ApiResponseErrorStatus errorStatus, int itemCount) {
        super(errorStatus);
        this.itemCount = itemCount;
    }

    public ApiPageResponse(int code, String message, T data, int itemCount) {
        super(code, message, data);
        this.itemCount = itemCount;
    }

    public ApiPageResponse(ApiResponse<T> response) {
        super(response.getCode(), response.getMsg(), response.getData());
    }
}
