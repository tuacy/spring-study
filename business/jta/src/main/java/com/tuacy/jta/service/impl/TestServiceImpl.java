package com.tuacy.jta.service.impl;

import com.tuacy.jta.entity.model.business.BusinessInfo;
import com.tuacy.jta.entity.model.system.SystemInfo;
import com.tuacy.jta.mapper.business.BusinessInfoMapper;
import com.tuacy.jta.mapper.system.SystemInfoMapper;
import com.tuacy.jta.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional()
    @Override
    public void transactional() {
        businessInfoMapper.insert("10", "10");
        systemInfoMapper.insert("10", "10");
        // 简单的抛出一个异常，只要上面的两个插入操作没有入库，就证明我们的配置成功
        throw new NullPointerException("aaaa");
    }
}
