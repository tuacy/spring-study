package com.tuacy.log.spring.boot.core.strategy;

import cn.hutool.core.date.DateUtil;
import com.tuacy.common.api.response.PageResponse;
import com.tuacy.common.api.response.Response;
import com.tuacy.common.api.response.ResponseErrorStatus;
import com.tuacy.common.api.param.BaseParam;
import com.tuacy.log.spring.boot.core.LogAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 业务日志，内容生成策略
 *
 * @name: LogContentStrategy
 * @author: tuacy.
 * @date: 2020/4/13.
 * @version: 1.0
 */
@Slf4j
public abstract class LogContentStrategy {

    private static final String SpEL_KEY_RESULT = "result";
    private static final String SpEL_KEY_IP = "ip";
    private static final String SpEL_KEY_NOW = "now";
    private static final String SpEL_KEY_ERROR_MESSAGE = "errorMessage";
    private static final String SpEL_KEY_EXCEPTION = "exception";

    private static LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
    private static ExpressionParser expressionParser = new SpelExpressionParser();

    /**
     * 方法执行成功
     *
     * @param annotation  对应的注解
     * @param successSpEl content el 表达式
     * @param pjp         环绕通知参数
     * @param result      方法执行的结果
     * @return content
     */
    public abstract String methodExecutionSuccess(LogAnnotation annotation, String successSpEl, ProceedingJoinPoint pjp, Object result);

    /**
     * 方法执行失败
     *
     * @param annotation 对应的注解
     * @param errorSpEl  content el 表达式
     * @param pjp        环绕通知参数
     * @param result     方法执行的结果
     * @return content
     */
    public abstract String methodExecutionError(LogAnnotation annotation, String errorSpEl, ProceedingJoinPoint pjp, Object result);

    /**
     * 方法执行的过程中有异常
     *
     * @param annotation    对应的注解
     * @param exceptionSpEl content el 表达式
     * @param pjp           环绕通知参数
     * @param ex            异常
     * @return content
     */
    public abstract String methodExecutionException(LogAnnotation annotation, String exceptionSpEl, ProceedingJoinPoint pjp, Exception ex);

    /**
     * 从ProceedingJoinPoint里面获取方法
     *
     * @param joinPoint ProceedingJoinPoint
     * @return 方法
     */
    protected Method getMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint
                        .getTarget()
                        .getClass()
                        .getDeclaredMethod(joinPoint.getSignature().getName(),
                                method.getParameterTypes());
            } catch (SecurityException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return method;
    }

    /**
     * 解析Spring EL表达式
     *
     * @param el     Spring EL表达式
     * @param method 方法
     * @param args   方法上的参数
     */
    protected String expressionParser(String el, Method method, Object[] args) {
        String[] params = discoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                context.setVariable(params[i], args[i]);
            }
        }
        // ip
        String ip = getIpAddr();
        if (ip != null) {
            context.setVariable(SpEL_KEY_IP, ip);
        }
        // 时间
        context.setVariable(SpEL_KEY_NOW, DateUtil.now());
        return expressionParser.parseExpression(el).getValue(context, String.class);
    }

    /**
     * 解析Spring EL表达式
     *
     * @param el     Spring EL表达式
     * @param method 方法
     * @param args   方法上的参数
     * @param result 请求返回的结果
     */
    protected String expressionParser(LogAnnotation annotation, String el, Method method, Object[] args, Object result) {
        String[] params = discoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                context.setVariable(params[i], args[i]);
            }
        }
        if (result != null) {
            context.setVariable(SpEL_KEY_RESULT, result);
        }
        // ip
        String ip = getIpAddr();
        if (ip != null) {
            context.setVariable(SpEL_KEY_IP, ip);
        }
        // 时间
        context.setVariable(SpEL_KEY_NOW, DateUtil.now());
        // 错误信息描述
        if (annotation != null) {
            Class<? extends LogErrorMessageStrategy> errorMessageStrategyClass = annotation.errorMessageStrategy();
            try {
                LogErrorMessageStrategy errorMessageStrategy = errorMessageStrategyClass.newInstance();
                context.setVariable(SpEL_KEY_ERROR_MESSAGE, errorMessageStrategy.errorMessage(result));
            } catch (Exception e) {
                e.printStackTrace();
                log.error("获取错误信息失败", e);
            }
        }
        return expressionParser.parseExpression(el).getValue(context, String.class);
    }

    /**
     * 解析Spring EL表达式
     *
     * @param el     Spring EL表达式
     * @param method 方法
     * @param args   方法上的参数
     * @param result 请求返回的结果
     */
    protected String expressionParser(String el, Method method, Object[] args, Object result) {
        String[] params = discoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                context.setVariable(params[i], args[i]);
            }
        }
        if (result != null) {
            context.setVariable(SpEL_KEY_RESULT, result);
        }
        // ip
        String ip = getIpAddr();
        if (ip != null) {
            context.setVariable(SpEL_KEY_IP, ip);
        }
        // 时间
        context.setVariable(SpEL_KEY_NOW, DateUtil.now());
        return expressionParser.parseExpression(el).getValue(context, String.class);
    }

    /**
     * 解析Spring EL表达式
     *
     * @param el        Spring EL表达式
     * @param method    方法
     * @param args      方法上的参数
     * @param exception 异常信息
     */
    protected String expressionParser(String el, Method method, Object[] args, Exception exception) {
        String[] params = discoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                context.setVariable(params[i], args[i]);
            }
        }
        // 异常
        if (exception != null) {
            context.setVariable(SpEL_KEY_EXCEPTION, exception);
        }
        // ip
        String ip = getIpAddr();
        if (ip != null) {
            context.setVariable(SpEL_KEY_IP, ip);
        }
        // 时间
        context.setVariable(SpEL_KEY_NOW, DateUtil.now());
        return expressionParser.parseExpression(el).getValue(context, String.class);
    }

    /**
     * 获取ip地址
     */
    protected String getIpAddr() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        HttpServletRequest request = attributes.getRequest();
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                try {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    ipAddress = inetAddress.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

            }
        }

        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        //"***.***.***.***".length() = 15
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }

        return ipAddress;
    }

    /**
     * 获取业务请求参数
     */
    protected BaseParam getRequestParam(ProceedingJoinPoint joinPoint) {
        for (Object argItem : joinPoint.getArgs()) {
            if (argItem instanceof BaseParam) {
                return (BaseParam) argItem;
            }
        }
        return null;
    }

    /**
     * 获取业务请求结果对应的code
     */
    protected int getResponseStatusCode(Object response) {
        if (response == null) {
            return ResponseErrorStatus.SUCCESS.getCode();
        }
        if (response instanceof PageResponse) {
            PageResponse responseResultBiz = (PageResponse) response;
            return responseResultBiz.getCode();
        } else if (response instanceof Response) {
            Response responsePageResultBiz = (Response) response;
            return responsePageResultBiz.getCode();
        } else {
            return ResponseErrorStatus.UNKNOWN_ERROR.getCode();
        }
    }

    /**
     * 获取业务请求结果对应的message信息
     */
    protected String getResponseMessage(Object response) {
        if (response == null) {
            return null;
        }
        if (response instanceof PageResponse) {
            PageResponse responseResultBiz = (PageResponse) response;
            return responseResultBiz.getMsg();
        } else if (response instanceof Response) {
            Response responsePageResultBiz = (Response) response;
            return responsePageResultBiz.getMsg();
        } else {
            return null;
        }
    }

}
