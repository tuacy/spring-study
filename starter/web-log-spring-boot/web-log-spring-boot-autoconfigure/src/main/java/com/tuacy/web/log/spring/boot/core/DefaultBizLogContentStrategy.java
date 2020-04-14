package com.tuacy.web.log.spring.boot.core;

import com.tuacy.web.log.spring.boot.aop.BizLogAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * @name: DefaultBizLogContentStrategy
 * @author: tuacy.
 * @date: 2020/4/13.
 * @version: 1.0
 * @Description:
 */
@Slf4j
public class DefaultBizLogContentStrategy extends BizLogContentStrategy {

    /**
     * 方法执行成功
     *
     * @param annotation  对应的注解
     * @param successSpEl content el 表达式
     * @param pjp         环绕通知参数
     * @param result      方法执行的结果
     * @return content
     */
    @Override
    public String methodExecutionSuccess(BizLogAnnotation annotation, String successSpEl, ProceedingJoinPoint pjp, Object result) {
        Method method = getMethod(pjp);
        Object[] args = pjp.getArgs();
        try {
            return expressionParser(successSpEl, method, args, result);
        } catch (Exception exception) {
            log.error("成功对应的SpEL表达式错误：" + successSpEl, exception);
            return "成功对应的SpEL表达式错误：" + successSpEl;
        }
    }

    /**
     * 方法执行失败
     *
     * @param annotation 对应的注解
     * @param errorSpEl  content el 表达式
     * @param pjp        环绕通知参数
     * @param result     方法执行的结果
     * @return content
     */
    @Override
    public String methodExecutionError(BizLogAnnotation annotation, String errorSpEl, ProceedingJoinPoint pjp, Object result) {
        Method method = getMethod(pjp);
        Object[] args = pjp.getArgs();
        try {
            return expressionParser(annotation, errorSpEl, method, args, result);
        } catch (Exception exception) {
            log.error("失败对应的SpEL表达式错误：" + errorSpEl, exception);
            return "失败对应的SpEL表达式错误：" + errorSpEl;
        }
    }

    /**
     * 方法执行的过程中有异常
     *
     * @param annotation    对应的注解
     * @param exceptionSpEl content el 表达式
     * @param pjp           环绕通知参数
     * @param ex            异常
     * @return content
     */
    @Override
    public String methodExecutionException(BizLogAnnotation annotation, String exceptionSpEl, ProceedingJoinPoint pjp, Exception ex) {
        Method method = getMethod(pjp);
        Object[] args = pjp.getArgs();
        try {
            return expressionParser(exceptionSpEl, method, args, ex);
        } catch (Exception exception) {
            log.error("异常对应的SpEL表达式错误：" + exceptionSpEl, exception);
            return "异常对应的SpEL表达式错误：" + exceptionSpEl;
        }
    }
}
