package com.tuacy.log.spring.boot.core;

import cn.hutool.core.util.ZipUtil;
import com.google.gson.Gson;
import com.tuacy.common.api.param.BaseParam;
import com.tuacy.common.api.response.PageResponse;
import com.tuacy.common.api.response.Response;
import com.tuacy.common.api.response.ResponseErrorStatus;
import com.tuacy.log.spring.boot.core.strategy.LogContentStrategy;
import com.tuacy.log.spring.boot.core.strategy.LogSuccessCheckStrategy;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

@Slf4j
@Aspect
@Component
public class LogAspect {

    private final static int BIZ_DB_CONTENT_LENGTH = 1000;

    private static Gson gson = new Gson();

    private static final String BIZ_LOG_INSERT = "insert into bizlog(modulename, methodname, requestip, requestparam, responsecode, loguserid, logusername, logtime, logcontent, logtype, bizstate) values(?,?,?,?,?,?,?,now(),?,?,?)";

    private JdbcTemplate jdbcTemplate;

    public LogAspect(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * aop切面，拦截所有包含BizLogAnnotation注解的方法
     */
    @Pointcut("@annotation(com.tuacy.log.spring.boot.core.LogAnnotation)")
    public void bizLog() {
    }

    @Around("bizLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Method method = getMethod(pjp);
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        // 日志类型
        int logType = logAnnotation.logType();
        // 模块名称
        String moduleName = logAnnotation.moduleName();
        // 内容策略
        Class<? extends LogContentStrategy> contentStrategyClass = logAnnotation.contentStrategy();
        LogContentStrategy contentStrategy = contentStrategyClass.newInstance();
        // 请求成功策略
        Class<? extends LogSuccessCheckStrategy> successStrategyClass = logAnnotation.successStrategy();
        LogSuccessCheckStrategy successStrategy = successStrategyClass.newInstance();
        Exception exception = null;
        Object result = null;
        try {
            result = pjp.proceed();
        } catch (Exception ex) {
            exception = ex;
        } finally {
            LogPo logPo = new LogPo();
            logPo.setModuleName(moduleName);
            logPo.setMethodName(method.getName());
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                logPo.setRequestIp(getIpAddr(request));
            }
            BaseParam baseParam = getRequestParam(pjp);
            if (baseParam != null) {
                logPo.setRequestParam(ZipUtil.gzip(gson.toJson(baseParam), "utf-8"));
                logPo.setLogUserId(String.valueOf(baseParam.getUserId()));
                logPo.setLogUserName(baseParam.getUserName());
            }
            if (result != null) {
                logPo.setResponseCode(String.valueOf(getResponseStatusCode(result)));
            }
            logPo.setLogType(logType);
            logPo.setLogTime(new Date());
            if (exception != null) {
                // 执行方法过程中产生异常
                logPo.setLogContent(contentStrategy.methodExecutionException(logAnnotation, logAnnotation.exceptionSpEl(), pjp, exception));
            } else {
                // 方法执行成功了，我们还需要通过方法的返回值判定操作是否成功
                if (successStrategy.isSuccess(result)) {
                    logPo.setLogContent(contentStrategy.methodExecutionSuccess(logAnnotation, logAnnotation.successSpEl(), pjp, result));
                } else {
                    logPo.setLogContent(contentStrategy.methodExecutionError(logAnnotation, logAnnotation.errorSpEl(), pjp, result));
                }
            }
            // 保存，入库
            saveBizLog(logPo);
        }
        if (exception != null) {
            throw exception;
        }
        return result;
    }

    /**
     * 业务日志保存入库
     */
    private void saveBizLog(LogPo logInfo) {
        if (jdbcTemplate != null && logInfo != null) {
            try {
                String content = logInfo.getLogContent();
                if (content != null && content.length() > BIZ_DB_CONTENT_LENGTH) {
                    content = content.substring(0, BIZ_DB_CONTENT_LENGTH);
                }
                ByteArrayInputStream requestParam = null;
                if (logInfo.getRequestParam() != null) {
                    requestParam = new ByteArrayInputStream(logInfo.getRequestParam());
                }
                int insertNum = jdbcTemplate.update(
                        BIZ_LOG_INSERT, logInfo.getModuleName(), logInfo.getMethodName(), logInfo.getRequestIp(),
                        requestParam, logInfo.getResponseCode(), logInfo.getLogUserId(),
                        logInfo.getLogUserName(), content, logInfo.getLogType(), logInfo.getBizState()
                );
                if (insertNum <= 0) {
                    log.error("保存业务日志失败, 插入数据失败");
                }
            } catch (Exception ex) {
                log.error("保存业务日志失败", ex);
            }

        }
    }

    /**
     * 获取请求参数
     */
    private BaseParam getRequestParam(ProceedingJoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof BaseParam) {
                return  (BaseParam) arg;
            }
        }
        return null;
    }

    /**
     * 从ProceedingJoinPoint里面获取方法
     *
     * @param joinPoint ProceedingJoinPoint
     * @return 方法
     */
    private Method getMethod(ProceedingJoinPoint joinPoint) {
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

    private String getIpAddr(HttpServletRequest request) {
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
     * 获取业务请求结果对应的code
     */
    private int getResponseStatusCode(Object response) {
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
}
