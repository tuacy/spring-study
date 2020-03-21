package com.tuacy.common.entity;

import java.util.Objects;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 21:47
 */
public abstract class BaseController {

    public ResponseResult setResult(ResponseSingle baseResp) {
        Objects.requireNonNull(baseResp, "请设置结果！");
        ResponseResult result = new ResponseResult();
        ResultType resultType = baseResp.getResultType();
        if (resultType == null) {
            resultType = ResultType.SUCCESS;
        }
        result.setStatus(resultType.getValue());
        result.setMsg(resultType.getDesc());
        result.setData(baseResp.getRespData());
        return result;
    }

    public ResponseResult setResult(ResponseList baseResp) {
        Objects.requireNonNull(baseResp, "请设置结果！");
        ResponseResult result = new ResponseResult();
        ResultType resultType = baseResp.getResultType();
        if (resultType == null) {
            resultType = ResultType.SUCCESS;
        }
        result.setStatus(resultType.getValue());
        result.setMsg(resultType.getDesc());
        result.setData(baseResp.getRespData());
        return result;
    }

}
