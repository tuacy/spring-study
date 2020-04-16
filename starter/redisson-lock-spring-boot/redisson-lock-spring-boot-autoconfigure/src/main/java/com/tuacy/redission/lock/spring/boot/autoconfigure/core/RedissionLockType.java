package com.tuacy.redission.lock.spring.boot.autoconfigure.core;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 20:13
 */
public enum RedissionLockType {
    /**
     * 可重入锁
     */
    REENTRANT_LOCK,

    /**
     * 公平锁
     */
    FAIR_LOCK,

    /**
     * 读锁
     */
    READ_LOCK,

    /**
     * 写锁
     */
    WRITE_LOCK;
}
