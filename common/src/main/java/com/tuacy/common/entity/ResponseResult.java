package com.tuacy.common.entity;

import lombok.Data;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 21:48
 */
@Data
public class ResponseResult {

    private int status;
    private String msg;
    private Object data;

}
