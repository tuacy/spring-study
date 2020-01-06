package com.tuacy.jta.service;

import com.tuacy.jta.entity.model.business.BusinessInfo;
import com.tuacy.jta.entity.model.system.SystemInfo;

/**
 * @name: ITestService
 * @author: tuacy.
 * @date: 2020/1/3.
 * @version: 1.0
 * @Description:
 */
public interface ITestService {

    BusinessInfo getBusinessInfo(String pkId);

    SystemInfo getSystemInfo(String pkId);

    void transactional();

}
