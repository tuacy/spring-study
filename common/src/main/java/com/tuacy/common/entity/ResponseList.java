package com.tuacy.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 22:18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResponseList<T> extends ResponseBase {

    private List<T> respData;

    public ResponseList() {
        this.resultType = ResultType.SUCCESS;
    }

}
