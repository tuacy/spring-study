package com.tuacy.component.spring.boot.data.redis;

/**
 * @version 1.0
 * @author: tuacy.
 * @date: 2020/4/20 20:43.
 */
public interface IRedisPing {

    /**
     * PING
     * 这个命令经常用来测试一个连接是否还是可用的
     * 起始版本：1.0.0
     *
     * @return pong
     */
    String ping();

    /**
     * PING
     * 这个命令经常用来测试一个连接是否还是可用的
     * 起始版本：1.0.0
     *
     * @param message 信息
     * @return message
     */
    String ping(String message);

}
