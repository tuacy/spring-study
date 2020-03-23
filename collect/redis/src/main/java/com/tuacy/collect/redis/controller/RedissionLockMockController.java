package com.tuacy.collect.redis.controller;

import com.tuacy.collect.redis.service.IRedissionLockMock;
import com.tuacy.common.entity.BaseController;
import com.tuacy.common.entity.ResponseResult;
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
public class RedissionLockMockController extends BaseController {

    private IRedissionLockMock redissionLockMock;

    @Autowired
    public void setRedissionLockMock(IRedissionLockMock redissionLockMock) {
        this.redissionLockMock = redissionLockMock;
    }

    @RequestMapping(value = "/simple", method = RequestMethod.POST)
    public ResponseResult simpleLock() {
        redissionLockMock.simpleLock();
        return setResult("ok");
    }

    @RequestMapping(value = "/elParam", method = RequestMethod.POST)
    public ResponseResult elParamLock() {
        redissionLockMock.elPramLock("wuyx");
        return setResult("ok");
    }

    @RequestMapping(value = "/elProperty", method = RequestMethod.POST)
    public ResponseResult elPropertyLock() {
        redissionLockMock.elPropertyLock();
        return setResult("ok");
    }

}
