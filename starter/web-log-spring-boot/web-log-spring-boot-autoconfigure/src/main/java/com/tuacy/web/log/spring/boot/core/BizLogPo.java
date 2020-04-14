package com.tuacy.web.log.spring.boot.core;

import lombok.Data;

import java.util.Date;

/**
 * @name: BizLogPo
 * @author: tuacy.
 * @date: 2020/4/13.
 * @version: 1.0
 * @Description:
 */
@Data
public class BizLogPo {
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
    private String requestParam;
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
}
