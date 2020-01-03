package com.tuacy.jta.service.impl;

import com.tuacy.jta.entity.model.business.BusinessInfo;
import com.tuacy.jta.entity.model.system.SystemInfo;
import com.tuacy.jta.mapper.business.BusinessInfoMapper;
import com.tuacy.jta.mapper.system.SystemInfoMapper;
import com.tuacy.jta.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @name: TestServiceImpl
 * @author: tuacy.
 * @date: 2020/1/3.
 * @version: 1.0
 * @Description:
 */
@Service
public class TestServiceImpl implements ITestService {

    private BusinessInfoMapper businessInfoMapper;
    private SystemInfoMapper systemInfoMapper;

    @Autowired
    public void setBusinessInfoMapper(BusinessInfoMapper businessInfoMapper) {
        this.businessInfoMapper = businessInfoMapper;
    }

    @Autowired
    public void setSystemInfoMapper(SystemInfoMapper systemInfoMapper) {
        this.systemInfoMapper = systemInfoMapper;
    }

    @Override
    public BusinessInfo getBusinessInfo(String pkId) {
        return businessInfoMapper.selectByPkId(pkId);
    }

    @Override
    public SystemInfo getSystemInfo(String pkId) {
        return systemInfoMapper.selectByPkId(pkId);
    }
}
