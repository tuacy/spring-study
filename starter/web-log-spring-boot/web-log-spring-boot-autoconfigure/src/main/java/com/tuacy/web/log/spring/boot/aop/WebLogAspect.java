package com.tuacy.web.log.spring.boot.aop;

import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @name: WebLogAspect
 * @author: tuacy.
 * @date: 2020/3/24.
 * @version: 1.0
 * @Description:
 */
@Aspect
@Data
public class WebLogAspect {

    @Pointcut("@annotation(com.tuacy.web.log.spring.boot.aop.WebLogOperation)")
    private void webLog() {

    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Method method = getMethod(pjp);
        Object[] args = pjp.getArgs();
        // 找到添加在方法上的WebLogOperation注解，获取响应的信息
        WebLogOperation webLogOperation = method.getAnnotation(WebLogOperation.class);
        try {
            return pjp.proceed();
        } finally {
            //TODO:
        }


//        // 记录开始时间
//        long startTime = System.currentTimeMillis();
//        //获取当前请求对象
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if (attributes == null) {
//
//        }
//        HttpServletRequest request = attributes.getRequest();
//        //记录请求信息
//        WebLog webLog = new WebLog();
//        Object result = joinPoint.proceed();
//        Signature signature = joinPoint.getSignature();
//        MethodSignature methodSignature = (MethodSignature) signature;
//        Method method = methodSignature.getMethod();
//        if (method.isAnnotationPresent(ApiOperation.class)) {
//            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
//            webLog.setDescription(apiOperation.value());
//        }
//        long endTime = System.currentTimeMillis();
//        String urlStr = request.getRequestURL().toString();
//        webLog.setBasePath(StrUtil.removeSuffix(urlStr, URLUtil.url(urlStr).getPath()));
//        webLog.setIp(request.getRemoteUser());
//        webLog.setMethod(request.getMethod());
//        webLog.setParameter(getParameter(method, joinPoint.getArgs()));
//        webLog.setResult(result);
//        webLog.setSpendTime((int) (endTime - startTime));
//        webLog.setStartTime(startTime);
//        webLog.setUri(request.getRequestURI());
//        webLog.setUrl(request.getRequestURL().toString());
//        LOGGER.info("{}", JSONUtil.parse(webLog));
//        return result;
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

}
