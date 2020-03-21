package com.tuacy.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 22:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResponseSingle<T> extends ResponseBase {
    public T respData;

    public ResponseSingle() {
        this.resultType = ResultType.SUCCESS;
    }

}
