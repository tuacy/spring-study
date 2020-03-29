package com.tuacy.zookeeper.starter.core.configure;

import lombok.Data;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * zookeeper 对应的配置文件
 *
 * @author wuyx
 * @version 1.0
 * @date 2020/3/29 11:12
 */
@ConfigurationProperties("zookeeper")
@Data
public class ZkProperties {

    /**
     * zookeeper地址
     */
    private String host;
    /**
     * 为了实现不同的Zookeeper业务之间的隔离，有的时候需要为每个业务分配一个独立的命名空间
     */
    private String nameSpace;
    /**
     * 会话超时时间，单位毫秒，默认60000ms
     */
    private int sessionTimeoutMs;
    /**
     * 连接创建超时时间，单位毫秒，默认60000ms
     */
    private int connectionTimeoutMs;

}
