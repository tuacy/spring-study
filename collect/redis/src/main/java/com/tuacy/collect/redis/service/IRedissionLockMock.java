package com.tuacy.collect.redis.service;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 22:40
 */
public interface IRedissionLockMock {

    /**
     * 直接指定lock key
     */
    void simpleLock();

    /**
     * lock key来源于函数参数
     */
    void elPramLock(String name);

    /**
     * lock key来源配置文件
     */
    void elPropertyLock();

}
