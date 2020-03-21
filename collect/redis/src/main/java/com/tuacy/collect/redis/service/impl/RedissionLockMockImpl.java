package com.tuacy.collect.redis.service.impl;

import com.tuacy.collect.redis.service.IRedissionLockMock;
import com.tuacy.redission.lock.spring.boot.autoconfigure.ERedissionLockType;
import com.tuacy.redission.lock.spring.boot.autoconfigure.RedissionLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 22:40
 */
@Service
@Slf4j
public class RedissionLockMockImpl implements IRedissionLockMock {

    @RedissionLock(key = "'tuacy'", lockType = ERedissionLockType.FAIR_LOCK)
    @Override
    public void simpleLock() {
        log.info("进入函数");
    }

    @RedissionLock(key = "#name", lockType = ERedissionLockType.FAIR_LOCK)
    @Override
    public void elPramLock(String name) {
        log.info("进入函数");
    }

    @RedissionLock(key = "${micor-services.basic-sms:basic-sms-provider}", lockType = ERedissionLockType.FAIR_LOCK)
    @Override
    public void elPropertyLock() {

    }
}
