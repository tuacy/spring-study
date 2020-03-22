package com.tuacy.redission.lock.spring.boot.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/22 8:17
 * redission分布式锁的一个简单封装
 */
@Slf4j
public class RedissionLock {

    /**
     * 获取锁等待时间(在指定时间内没有获取到锁，就认为获取失败)，默认3秒
     */
    private final static long DEFAULT_WAIT_TIME = 3000L;
    /**
     * 锁的租赁时间，换句话说就是锁自动释放时间(防止锁一直不释放)，默认30秒
     */
    private final static long DEFAULT_LEASE_TIME = 30000L;
    private final static TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MILLISECONDS;

    private RLock rLock;

    public RedissionLock(RedissonClient redissonClient, String key, RedissionLockType lockType) {
        this.rLock = getLock(redissonClient, key, lockType);
    }

    /**
     * 阻塞直到获取锁，可以中断
     *
     * @param leaseTime 锁的租赁时间，换句话说就是锁自动释放时间,持锁时间(防止锁一直不释放)
     * @param unit      时间单位(获取锁等待时间和持锁时间都用此单位)
     * @throws InterruptedException
     */
    public void lockInterruptibly(long leaseTime, TimeUnit unit) throws InterruptedException {
        rLock.lockInterruptibly(leaseTime, unit);
    }

    /**
     * 阻塞直到获取锁
     *
     * @param leaseTime 锁的租赁时间，换句话说就是锁自动释放时间,持锁时间(防止锁一直不释放)
     * @param unit      时间单位(获取锁等待时间和持锁时间都用此单位)
     */
    public void lock(long leaseTime, TimeUnit unit) {
        rLock.lock(leaseTime, unit);
    }

    /**
     * 获取锁
     * 获取锁等待时间(在指定时间内没有获取到锁，就认为获取失败)，默认3秒
     * 锁的租赁时间，换句话说就是锁自动释放时间(防止锁一直不释放)，默认30秒
     *
     * @return 获取锁是否成功
     */
    public boolean tryLock() {
        return tryLock(DEFAULT_WAIT_TIME, DEFAULT_LEASE_TIME, DEFAULT_TIME_UNIT);
    }

    /**
     * 获取锁
     *
     * @param waitTime  获取锁等待时间(在指定时间内没有获取到锁，就认为获取失败)，默认3秒
     * @param leaseTime 锁的租赁时间，换句话说就是锁自动释放时间(防止锁一直不释放)，默认30秒
     * @param unit      时间单位(获取锁等待时间和持锁时间都用此单位)
     * @return 获取锁是否成功
     */
    public boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) {
        try {
            return rLock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException exception) {
            log.error("获取锁失败", exception);
            return false;
        }
    }


    /**
     * 是否处于锁住的状态
     *
     * @return 是否处于锁住的状态
     */
    public boolean isLocked() {
        return rLock.isLocked();
    }

    /**
     * 释放锁
     */
    public void unlock() {
        rLock.unlock();
    }

    private RLock getLock(RedissonClient redissonClient, String key, RedissionLockType lockType) {
        Objects.requireNonNull(lockType, "锁的类型不能为空！");
        switch (lockType) {
            case REENTRANT_LOCK:
                // 可重入锁
                return redissonClient.getLock(key);
            case FAIR_LOCK:
                // 公平锁
                return redissonClient.getFairLock(key);
            case READ_LOCK:
                // 读锁
                return redissonClient.getReadWriteLock(key).readLock();
            case WRITE_LOCK:
                // 写锁
                return redissonClient.getReadWriteLock(key).writeLock();

            default:
                throw new RuntimeException("不支持的锁类型:" + lockType.name());
        }
    }
}
