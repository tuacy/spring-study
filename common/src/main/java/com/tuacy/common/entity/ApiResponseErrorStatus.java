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
public class ApiResponseErrorStatus {

    /**
     * 成功
     */
    public static ApiResponseErrorStatus SUCCESS = new ApiResponseErrorStatus(200, "success");

    /**
     * 400错误，bad request
     */
    public static ApiResponseErrorStatus ERROR_400 = new ApiResponseErrorStatus(400, "bad request");
    /**
     * 401错误，未授权
     */
    public static ApiResponseErrorStatus ERROR_401 = new ApiResponseErrorStatus(401, "未授权");
    /**
     * 403错误，权限不足
     */
    public static ApiResponseErrorStatus ERROR_403 = new ApiResponseErrorStatus(403, "权限不足");
    /**
     * 404错误，处理器不存在异常，资源不存在异常
     */
    public static ApiResponseErrorStatus ERROR_404 = new ApiResponseErrorStatus(404, "404");
    /**
     * 500异常,服务器内部错误
     */
    public static ApiResponseErrorStatus ERROR_500 = new ApiResponseErrorStatus(500, "500");


    public static ApiResponseErrorStatus SERVER_ERROR = new ApiResponseErrorStatus(50000, "服务端异常");
    public static ApiResponseErrorStatus UNKNOWN_ERROR = new ApiResponseErrorStatus(50001, "服务端异常");

    private int code;
    private String message;

    public ApiResponseErrorStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
