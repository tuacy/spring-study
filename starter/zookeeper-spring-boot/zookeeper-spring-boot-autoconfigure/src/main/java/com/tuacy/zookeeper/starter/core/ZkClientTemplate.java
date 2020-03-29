package com.tuacy.zookeeper.starter.core;

import com.tuacy.zookeeper.starter.core.configure.ZkProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/29 11:22
 */
@Slf4j
public class ZkClientTemplate {

    /**
     * zookeeper客户端连接参数
     */
    private ZkProperties properties;
    /**
     * zookeeper客户端实例
     */
    private CuratorFramework client;

    public ZkClientTemplate(ZkProperties properties) {
        this.properties = properties;
    }

    /**
     * 构造函数之后，做初始化处理
     */
    @PostConstruct
    public void onPostConstruct() {
    }

    /**
     * 对象销毁前做回收处理
     */
    @PreDestroy
    public void onPreDestroy() {

    }

}
