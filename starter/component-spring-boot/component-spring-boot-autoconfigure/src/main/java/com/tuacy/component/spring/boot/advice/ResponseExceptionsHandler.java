package com.tuacy.component.spring.boot.advice;

import com.tuacy.common.entity.ResponseResult;
import com.tuacy.common.exception.ResponseException;
import com.tuacy.common.utils.ResponseResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常处理类
 *
 * @name: ResponseExceptionsHandler
 * @author: tuacy.
 * @date: 2020/3/23.
 * @version: 1.0
 */
@Slf4j
@RestControllerAdvice
public class ResponseExceptionsHandler {

    /**
     * 我们一些自定义异常的处理
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseResult<?> bizExceptionHandler(Exception exception) {
        log.error("请求异常", exception);
        if (exception instanceof ResponseException) {
            return ResponseResultUtil.eroor((ResponseException) exception);
        } else {
            return ResponseResultUtil.eroor();
        }
    }

    /**
     * 用来捕获404，400这种无法到达controller的错误
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseResult<?> defaultErrorHandler(Exception exception) {
        log.error("请求异常", exception);
        if (exception instanceof NoHandlerFoundException) {
            return ResponseResultUtil.error404();
        } else {
            return ResponseResultUtil.error500();
        }
    }

}
