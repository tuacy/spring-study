package com.tuacy.common.entity;

import lombok.Data;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 22:22
 */
@Data
public abstract class ResponseBase {

    protected ResultType resultType;

    /**
     * 自定义错误信息
     * @param customerErrorMsg 自定义的错误信息
     */
    public void setCustomerErrorMsg(String customerErrorMsg) {
        this.resultType = ResultType.OTHER_ERROR;
        this.resultType.setDesc(customerErrorMsg);
    }

}
