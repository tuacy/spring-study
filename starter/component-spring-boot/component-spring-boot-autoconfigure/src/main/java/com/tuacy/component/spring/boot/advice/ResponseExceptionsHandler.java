package com.tuacy.component.spring.boot.advice;

import com.tuacy.common.api.exception.*;
import com.tuacy.common.api.response.Response;
import com.tuacy.common.exception.ResponseException;
import com.tuacy.common.utils.ResponseResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * 全局异常处理类
 * <p>
 * ps: 如果出现404,500等错误捕获不到的情况，请添加如下配置
 * spring.mvc.throw-exception-if-no-handler-found=true
 * spring.mvc.static-path-pattern=/statics/**
 *
 * @name: ResponseExceptionsHandler
 * @author: tuacy.
 * @date: 2020/3/23.
 * @version: 1.0
 * <p>
 * https://my.oschina.net/u/3049656/blog/1798583
 */
@Slf4j
@RestControllerAdvice
public class ResponseExceptionsHandler extends ResponseEntityExceptionHandler {

    /**
     * 这个汇总处理方法，将响应数据替换为我们的定义的格式
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception exception, Object body, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        log.error("请求异常", exception);
        // 将响应数据替换为我们的定义的格式
        return new ResponseEntity<>(new Response(status.value(), exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        if (!CollectionUtils.isEmpty(objectErrors)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ObjectError objectError : objectErrors) {
                msgBuilder.append(objectError.getDefaultMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }
            return new ResponseEntity<>(ResponseResultUtil.errorParam().setMsg(errorMessage), status);
        } else {
            return super.handleMethodArgumentNotValid(ex, headers, status, request);
        }
    }

    /**
     * 业务异常处理
     */
    @ExceptionHandler(value = ResponseException.class)
    public Response<?> bizExceptionHandler(Exception exception) {
        log.error("请求异常", exception);
        return ResponseResultUtil.error((ResponseException) exception);
    }

    /**
     * 用来处理bean validation异常
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public Response<?> constraintViolationException(ConstraintViolationException exception) {
        log.error("ConstraintViolationException", exception);
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ConstraintViolation constraintViolation : constraintViolations) {
                msgBuilder.append(constraintViolation.getMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }
            return ResponseResultUtil.errorParam().setMsg(errorMessage);
        } else {
            return ResponseResultUtil.errorParam().setMsg(exception.getMessage());
        }
    }

    /**
     * 400错误，bad request
     */
    @ExceptionHandler(value = {IllegalArgumentException.class, Exception400.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<?> badRequestException(Exception exception) {
        log.error("400", exception);
        return ResponseResultUtil.error400().setMsg(exception.getMessage());
    }

    /**
     * 401错误，未授权
     */
    @ExceptionHandler(value = {Exception401.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<?> unauthorizedException(Exception exception) {
        log.error("400", exception);
        return ResponseResultUtil.error401().setMsg(exception.getMessage());
    }

    /**
     * 403错误，权限不足
     */
    @ExceptionHandler(value = {Exception403.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<?> forbiddenException(Exception exception) {
        log.error("400", exception);
        return ResponseResultUtil.error403().setMsg(exception.getMessage());
    }

    /**
     * 404错误，处理器不存在异常，资源不存在异常
     */
    @ExceptionHandler(value = {Exception404.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response<?> noHandlerFoundException(Exception exception) {
        log.error("400", exception);
        return ResponseResultUtil.error404().setMsg(exception.getMessage());
    }

    /**
     * 500异常
     */
    @ExceptionHandler(value = {Exception500.class, Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<?> exception(Exception exception) {
        log.error("400", exception);
        return ResponseResultUtil.error500().setMsg(exception.getMessage());
    }

}
