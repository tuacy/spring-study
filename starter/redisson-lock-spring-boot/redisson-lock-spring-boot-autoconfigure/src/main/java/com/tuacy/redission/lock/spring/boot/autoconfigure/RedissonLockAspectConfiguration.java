package com.tuacy.redission.lock.spring.boot.autoconfigure;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 20:15
 */
@Aspect
@Configuration
@ConditionalOnBean(RedissonClient.class)
@AutoConfigureAfter(RedissonAutoConfiguration.class)
public class RedissonLockAspectConfiguration {

    private final Logger logger = LoggerFactory.getLogger(RedissonLockAspectConfiguration.class);

    private static LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
    private static ExpressionParser expressionParser = new SpelExpressionParser();

    private RedissonClient redissonClient;

    @Autowired
    public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Pointcut("@annotation(com.tuacy.redission.lock.spring.boot.autoconfigure.RedissionLockAnnotate)")
    private void lockPoint() {

    }

    @Around("lockPoint()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 找到对应的方法
        Method method = getMethod(pjp);
        Object[] args = pjp.getArgs();
        // 找到添加在方法上的RedissionLock注解，获取响应的信息
        RedissionLockAnnotate lockAction = method.getAnnotation(RedissionLockAnnotate.class);
        // Spring EL表达式
        String expression = lockAction.key();
        Objects.requireNonNull(expression, "请指定锁对应的key(Spring EL表达式)！");
        String lockKey = expressionParser(expression, method, args);
        logger.debug("锁对应的key [{}]", lockKey);
        // 获取锁
        RLock lock = getLock(lockKey, lockAction);
        if (!lock.tryLock(lockAction.waitTime(), lockAction.leaseTime(), lockAction.unit())) {
            logger.debug("获取锁失败 [{}]", lockKey);
            return null;
        }

        // 得到锁,执行方法，释放锁
        logger.debug("获取锁成功 [{}]", lockKey);
        try {
            return pjp.proceed();
        } finally {
            lock.unlock();
            logger.debug("释放锁 [{}]", lockKey);
        }
    }

    /**
     * 解析Spring EL表达式
     *
     * @param el     Spring EL表达式
     * @param method 方法
     * @param args   方法上的参数
     */
    private String expressionParser(String el, Method method, Object[] args) {
        String[] params = discoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                context.setVariable(params[i], args[i]);
            }
        }
        return expressionParser.parseExpression(el).getValue(context, String.class);
    }

    private RLock getLock(String key, RedissionLockAnnotate lockAction) {
        Objects.requireNonNull(lockAction, "锁的类型不能为空！");
        switch (lockAction.lockType()) {
            case REENTRANT_LOCK:
                // 可重入锁
                return redissonClient.getLock(key);
            case FAIR_LOCK:
                // 公平锁
                return redissonClient.getFairLock(key);
            case READ_LOCK:
                // 读锁
                return redissonClient.getReadWriteLock(key).readLock();
            case WRITE_LOCK:
                // 写锁
                return redissonClient.getReadWriteLock(key).writeLock();

            default:
                throw new RuntimeException("不支持的锁类型:" + lockAction.lockType().name());
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
}
