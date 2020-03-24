package com.tuacy.web.log.spring.boot.store;

import com.tuacy.web.log.spring.boot.aop.WebLogEntity;

/**
 *
 * api 保存接口
 *
 * @name: IWebLogStore
 * @author: tuacy.
 * @date: 2020/3/24.
 * @version: 1.0
 */
public interface IWebLogStore {

    /**
     * 保存日志信息
     */
    void storeWebLog(WebLogEntity webLogEntity);

}
