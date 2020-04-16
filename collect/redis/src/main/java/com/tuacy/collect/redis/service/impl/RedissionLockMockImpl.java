package com.tuacy.collect.redis.service.impl;

import com.tuacy.collect.redis.service.IRedissionLockMock;
import com.tuacy.redission.lock.spring.boot.autoconfigure.core.RedissionLockAnnotate;
import com.tuacy.redission.lock.spring.boot.autoconfigure.core.RedissionLockType;
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

    @RedissionLockAnnotate(key = "'tuacy'", lockType = RedissionLockType.FAIR_LOCK)
    @Override
    public void simpleLock() {
        log.info("进入函数");
    }

    @RedissionLockAnnotate(key = "#name", lockType = RedissionLockType.FAIR_LOCK)
    @Override
    public void elPramLock(String name) {
        log.info("进入函数");
    }

    @RedissionLockAnnotate(key = "${micor-services.basic-sms:basic-sms-provider}", lockType = RedissionLockType.FAIR_LOCK)
    @Override
    public void elPropertyLock() {

    }
}
