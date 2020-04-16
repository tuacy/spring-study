package com.tuacy.collect.redis.controller;

import com.tuacy.collect.redis.entity.param.SimpleLockParam;
import com.tuacy.collect.redis.service.IRedissionLockMock;
import com.tuacy.common.api.controller.BaseController;
import com.tuacy.common.api.param.BaseParam;
import com.tuacy.common.api.response.Response;
import com.tuacy.log.spring.boot.core.LogAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    @LogAnnotation(
            moduleName = "测试",
            logType = 1,
            successSpEl = "'成功'",
            errorSpEl = "'失败'"
    )
    public Response simpleLock(@Valid @RequestBody SimpleLockParam param) {
        redissionLockMock.simpleLock();
        return setResult("ok");
    }

    @RequestMapping(value = "/elParam", method = RequestMethod.POST)
    public Response elParamLock(@RequestBody BaseParam param) {
        redissionLockMock.elPramLock("wuyx");
        return setResult("ok");
    }

    @RequestMapping(value = "/elProperty", method = RequestMethod.POST)
    public Response elPropertyLock(@RequestBody BaseParam param) {
        redissionLockMock.elPropertyLock();
        return setResult("ok");
    }

}
