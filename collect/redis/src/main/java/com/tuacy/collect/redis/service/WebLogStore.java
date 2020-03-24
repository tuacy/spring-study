package com.tuacy.collect.redis.service;

import com.tuacy.web.log.spring.boot.aop.WebLogEntity;
import com.tuacy.web.log.spring.boot.store.IWebLogStore;
import org.springframework.stereotype.Service;

/**
 * @name: WebLogStore
 * @author: tuacy.
 * @date: 2020/3/24.
 * @version: 1.0
 * @Description:
 */
@Service
public class WebLogStore implements IWebLogStore {

    @Override
    public void storeWebLog(WebLogEntity webLogEntity) {
    }
}
