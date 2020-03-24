package com.tuacy.web.log.spring.boot.aop;

import lombok.Data;

/**
 * api 访问日志封装
 *
 * @name: WebLogEntity
 * @author: tuacy.
 * @date: 2020/3/24.
 * @version: 1.0
 */
@Data
public class WebLogEntity {
    /**
     * 操作描述
     */
    private String description;

    /**
     * 操作时间
     */
    private Long startTime;

    /**
     * 消耗时间
     */
    private Long spendTime;

    /**
     * 根路径
     */
    private String basePath;

    /**
     * URI
     */
    private String uri;

    /**
     * URL
     */
    private String url;

    /**
     * 请求类型
     */
    private String method;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 请求参数
     */
    private String parameter;

    /**
     * 请求返回的结果
     */
    private String result;

    /**
     * 请求是否成功
     */
    private boolean success;

    /**
     * 请求状态描述，如果请求失败，则该字段放到饿是异常信息
     */
    private String message;

}
