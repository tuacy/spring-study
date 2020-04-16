package com.tuacy.log.spring.boot.core;

import lombok.Data;

import java.util.Date;

/**
 * @name: LogPo
 * @author: tuacy.
 * @date: 2020/4/13.
 * @version: 1.0
 * @Description:
 */
@Data
public class LogPo {
    public static final int BIZ_STATUE_SUCCESS = 0;
    public static final int BIZ_STATUE_ERROR = 1;
    public static final int BIZ_STATUE_EXCEPTION = 2;

    /**
     * 模块名称
     */
    private String moduleName;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 请求ip地址
     */
    private String requestIp;
    /**
     * 请求参数
     */
    private byte[] requestParam;
    /**
     * 请求结果code
     */
    private String responseCode;
    /**
     * 操作用户id
     */
    private String logUserId;
    /**
     * 操作用户名
     */
    private String logUserName;
    /**
     * 操作时间
     */
    private Date logTime;
    /**
     * 操作内容
     */
    private String logContent;
    /**
     * 类型
     */
    private Integer logType;
    /**
     * 业务状态，因为请求成功，但是业务不一定成功
     * 0：成功，1：失败
     */
    private int bizState;
}
