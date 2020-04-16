package com.tuacy.redission.lock.spring.boot.autoconfigure.core;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 20:19
 * <p>
 * 用于添加在方法上的一个注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RedissionLockAnnotate {

    /**
     * Spring El表达式
     * 举例子：
     * 1： lock key是字符串tuacy         则对应  @RedissionLock(key = "'tuacy'", lockType = ERedissionLockType.FAIR_LOCK)
     * 2： lock key用函数name对应的参数   则对应  @RedissionLock(key = "#name", lockType = ERedissionLockType.FAIR_LOCK)
     */
    String key() default "'default'";

    /**
     * 锁类型(可重入锁、FAIR_LOCK、READ_LOCK、WRITE_LOCK)
     */
    RedissionLockType lockType() default RedissionLockType.REENTRANT_LOCK;

    /**
     * 获取锁等待时间(在指定时间内没有获取到锁，就认为获取失败)，默认3秒
     */
    long waitTime() default 3000L;

    /**
     * 锁的租赁时间，换句话说就是锁自动释放时间(防止锁一直不释放)，默认30秒
     */
    long leaseTime() default 30000L;

    /**
     * 时间单位(获取锁等待时间和持锁时间都用此单位)
     */
    TimeUnit unit() default TimeUnit.MILLISECONDS;

}
