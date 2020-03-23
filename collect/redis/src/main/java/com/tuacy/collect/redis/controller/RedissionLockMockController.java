package com.tuacy.collect.redis.controller;

import com.tuacy.collect.redis.service.IRedissionLockMock;
import com.tuacy.common.entity.ApiBaseController;
import com.tuacy.common.entity.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 21:42
 */
@RestController
@RequestMapping("/v1/redissionLock")
public class RedissionLockMockController extends ApiBaseController {

    private IRedissionLockMock redissionLockMock;

    @Autowired
    public void setRedissionLockMock(IRedissionLockMock redissionLockMock) {
        this.redissionLockMock = redissionLockMock;
    }

    @RequestMapping(value = "/simple", method = RequestMethod.POST)
    public ApiResponse simpleLock() {
        redissionLockMock.simpleLock();
        return setResult("ok");
    }

    @RequestMapping(value = "/elParam", method = RequestMethod.POST)
    public ApiResponse elParamLock() {
        redissionLockMock.elPramLock("wuyx");
        return setResult("ok");
    }

    @RequestMapping(value = "/elProperty", method = RequestMethod.POST)
    public ApiResponse elPropertyLock() {
        redissionLockMock.elPropertyLock();
        return setResult("ok");
    }

}
