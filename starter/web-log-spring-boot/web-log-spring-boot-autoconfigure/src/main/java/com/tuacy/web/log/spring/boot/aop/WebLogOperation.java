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


}
