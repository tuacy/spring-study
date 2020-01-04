package com.tuacy.jta.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @name: BusinessDataSourceProperties
 * @author: tuacy.
 * @date: 2020/1/4.
 * @version: 1.0
 * @Description:
 */
@Data
@ConfigurationProperties(prefix = "spring.datasource.druid.system-db")
public class SystemDataSourceProperties {

    private String url;
    private String username;
    private String password;
    private int minPoolSize;
    private int maxPoolSize;
    private int maxLifetime;
    private int borrowConnectionTimeout;
    private int loginTimeout;
    private int maintenanceInterval;
    private int maxIdleTime;
    private String testQuery;

}
