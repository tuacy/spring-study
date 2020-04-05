package com.tuacy.common.exception;

import com.tuacy.common.entity.response.ApiResponseErrorStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 接口异常封装
 *
 * @name: ResponseException
 * @author: tuacy.
 * @date: 2020/3/23.
 * @version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResponseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ApiResponseErrorStatus errorStatus;

    public ResponseException(ApiResponseErrorStatus status) {
        super(status.getMessage());
        this.errorStatus = status;
    }

}
