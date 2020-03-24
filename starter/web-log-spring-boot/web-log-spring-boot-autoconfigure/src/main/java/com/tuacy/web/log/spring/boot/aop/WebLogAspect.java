package com.tuacy.web.log.spring.boot.aop;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.google.gson.Gson;
import com.tuacy.web.log.spring.boot.store.IWebLogStore;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * api 访问日志切面
 *
 * @name: WebLogAspect
 * @author: tuacy.
 * @date: 2020/3/24.
 * @version: 1.0
 */
@Aspect
public class WebLogAspect {

    private static Gson gson = new Gson();

    private IWebLogStore webLogStore;

    @Autowired
    public void setWebLogStore(IWebLogStore webLogStore) {
        this.webLogStore = webLogStore;
    }

    @Pointcut("@annotation(com.tuacy.web.log.spring.boot.aop.WebLogOperation)")
    private void webLog() {

    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        //获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (attributes != null) {
            request = attributes.getRequest();
        }
        Object ret = null;
        String exceptionMessage = null;
        try {
            // 执行目标方法
            ret = joinPoint.proceed();
            return ret;
        } catch (Exception ex) {
            exceptionMessage = ex.getMessage();
            throw ex;
        } finally {
            // 保存操作日志信息
            WebLogEntity webLogEntity = generateWebLogEntity(startTime, System.currentTimeMillis(), request, joinPoint);
            if (exceptionMessage != null) {
                webLogEntity.setResult(null);
                webLogEntity.setMessage(exceptionMessage);
            } else {
                // 结果正常返回
                if (ret != null) {
                    webLogEntity.setResult(gson.toJson(ret));
                }
                webLogEntity.setMessage("OK");
                webLogEntity.setSuccess(true);
            }
            webLogStore.storeWebLog(webLogEntity);
        }
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

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private String getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int index = 0; index < parameters.length; index++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[index].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[index]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[index].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[index].getName();
                if (!StringUtils.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[index]);
                argList.add(map);
            }
        }
        if (argList.size() == 0) {
            return null;
        } else if (argList.size() == 1) {
            return gson.toJson(argList.get(0));
        } else {
            return gson.toJson(argList);
        }
    }

    /**
     * 获取地址
     */
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
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                if (inet != null) {
                    ipAddress = inet.getHostAddress();
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

    private WebLogEntity generateWebLogEntity(long startTime, long endTime, HttpServletRequest request, ProceedingJoinPoint joinPoint) {
        Method method = getMethod(joinPoint);
        WebLogOperation logOperation = method.getAnnotation(WebLogOperation.class);
        WebLogEntity webLogEntity = new WebLogEntity();
        webLogEntity.setDescription(logOperation.desc());
        webLogEntity.setStartTime(startTime);
        webLogEntity.setSpendTime(endTime - startTime);
        webLogEntity.setParameter(getParameter(method, joinPoint.getArgs()));
        if (request != null) {
            String urlStr = request.getRequestURL().toString();
            webLogEntity.setBasePath(StrUtil.removeSuffix(urlStr, URLUtil.url(urlStr).getPath()));
            webLogEntity.setUri(request.getRequestURI());
            webLogEntity.setUrl(request.getRequestURL().toString());
            webLogEntity.setMethod(request.getMethod());
            webLogEntity.setIp(logOperation.ipRequire() ? getIpAddr(request) : null);
        }
        return webLogEntity;
    }

}
