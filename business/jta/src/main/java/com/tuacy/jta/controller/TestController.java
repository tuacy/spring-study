package com.tuacy.jta.controller;

import com.tuacy.jta.entity.model.business.BusinessInfo;
import com.tuacy.jta.entity.model.system.SystemInfo;
import com.tuacy.jta.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @name: TestController
 * @author: tuacy.
 * @date: 2020/1/3.
 * @version: 1.0
 * @Description:
 */
@RestController
@RequestMapping("/v1/jta/test")
public class TestController {

    private ITestService testService;

    @Autowired
    public void setTestService(ITestService testService) {
        this.testService = testService;
    }

    /**
     * 测试system数据源是否配置正确
     */
    @RequestMapping(value = "/systemDbSourceTest", method = RequestMethod.POST)
    public String systemDbSourceTest() {
        SystemInfo systemInfo = testService.getSystemInfo("1");
        return "success";
    }

    /**
     * 测试business数据源是否配置正确
     */
    @RequestMapping(value = "/businessDbSourceTest", method = RequestMethod.POST)
    public String businessDbSourceTest() {
        BusinessInfo businessInfo = testService.getBusinessInfo("1");
        return "success";
    }

    /**
     * 测试分布式事务,写一个很简单的测试
     */
    @RequestMapping(value = "/transaction", method = RequestMethod.POST)
    public String transactional() {
        testService.transactional();
        return "success";
    }

}
