package com.tuacy.web.log.spring.boot.aop;

import java.lang.annotation.*;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 20:19
 * <p>
 * 用于添加在方法上的一个注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface WebLogOperation {

    /**
     * 描述信息
     */
    String desc() default "";

    /**
     * 是否需要保存ip
     */
    boolean ipRequire() default true;

    /**
     * 是否需要保存参数
     */
    boolean parameterRequire() default true;

    /**
     * 是否需要保存结果
     */
    boolean resultRequire() default true;

}
