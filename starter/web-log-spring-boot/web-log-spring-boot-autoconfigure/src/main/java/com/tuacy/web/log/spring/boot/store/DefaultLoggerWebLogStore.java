package com.tuacy.web.log.spring.boot.store;

import cn.hutool.json.JSONUtil;
import com.tuacy.web.log.spring.boot.aop.WebLogEntity;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * api 保存接口
 *
 * @name: DefaultLoggerWebLogStore
 * @author: tuacy.
 * @date: 2020/3/24.
 * @version: 1.0
 */
@Slf4j
public class DefaultLoggerWebLogStore implements IWebLogStore {
    /**
     * 这里只是简单的打印
     */
    @Override
    public void storeWebLog(WebLogEntity webLogEntity) {
        log.info("{}", JSONUtil.parse(webLogEntity));
    }
}
