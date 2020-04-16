package com.tuacy.common.api.response;

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

    /**
     * 成功
     */
    public static ResponseErrorStatus SUCCESS = new ResponseErrorStatus(200, "success");

    /**
     * 400错误，bad request
     */
    public static ResponseErrorStatus ERROR_400 = new ResponseErrorStatus(400, "bad request");
    /**
     * 401错误，未授权
     */
    public static ResponseErrorStatus ERROR_401 = new ResponseErrorStatus(401, "未授权");
    /**
     * 403错误，权限不足
     */
    public static ResponseErrorStatus ERROR_403 = new ResponseErrorStatus(403, "权限不足");
    /**
     * 404错误，处理器不存在异常，资源不存在异常
     */
    public static ResponseErrorStatus ERROR_404 = new ResponseErrorStatus(404, "404");
    /**
     * 500异常,服务器内部错误
     */
    public static ResponseErrorStatus ERROR_500 = new ResponseErrorStatus(500, "500");

    /**
     * 500异常,服务器内部错误
     */
    public static ResponseErrorStatus ERROR_PARAM = new ResponseErrorStatus(50000, "参数异常");
    public static ResponseErrorStatus SERVER_ERROR = new ResponseErrorStatus(50001, "服务端异常");
    public static ResponseErrorStatus UNKNOWN_ERROR = new ResponseErrorStatus(50002, "服务端异常");

    private int code;
    private String message;

    public ResponseErrorStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
