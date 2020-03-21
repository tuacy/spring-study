package com.tuacy.component.spring.boot.conditional;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 21:01
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(OnPropertyExistCondition.class)
public @interface ConditionalOnPropertyExist {

    /**
     * 配置文件里面对应的key
     */
    String name() default "";

    /**
     * 是否有配置的时候判断通过
     */
    boolean exist() default true;

}
