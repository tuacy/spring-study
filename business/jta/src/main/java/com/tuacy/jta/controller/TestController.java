package com.tuacy.jta.controller;

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
     * 获取区域树
     */
    @RequestMapping(value = "/systemDbSourceTest", method = RequestMethod.POST)
    public String listAreaTree() {

        testService.getBusinessInfo("1");

        return "success";
    }

}
