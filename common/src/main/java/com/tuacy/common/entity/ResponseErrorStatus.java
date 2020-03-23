package com.tuacy.common.entity;

import lombok.Data;

/**
 * 请求状态码和消息封装
 *
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 21:59
 */
@Data
public class ResponseErrorStatus {

    public static ResponseErrorStatus SUCCESS = new ResponseErrorStatus(0, "success");
    public static ResponseErrorStatus ERROR_404 = new ResponseErrorStatus(404, "404");
    public static ResponseErrorStatus ERROR_500 = new ResponseErrorStatus(500, "500");
    public static ResponseErrorStatus SERVER_ERROR = new ResponseErrorStatus(50000, "服务端异常");
    public static ResponseErrorStatus UNKNOWN_ERROR = new ResponseErrorStatus(50001, "服务端异常");

    private int code;
    private String message;

    public ResponseErrorStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
