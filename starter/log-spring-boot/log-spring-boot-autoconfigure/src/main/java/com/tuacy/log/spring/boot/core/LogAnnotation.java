package com.tuacy.log.spring.boot.core;


import com.tuacy.log.spring.boot.core.strategy.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 业务日志注解
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {

    /**
     * 日志类型
     */
    int logType() default 0;

    /**
     * 模块名称
     */
    String moduleName() default "";

    /**
     * 操作成功对应的SpEL表达式
     * 举例子说明：
     * 情况一 -> 使用固定的字符串(tuacy)
     * ps:  @LogAnnotation(successSpEl = "'tuacy'")
     * 情况二 -> 使用函数的某个参数(name是函数的某个参数)
     * ps:  @LogAnnotation(successSpEl = "#name")
     * 情况三 -> 使用函数的某个参数的某个属性(name是函数的某个参数)
     * ps:  @LogAnnotation(successSpEl = "#loginParam.clientSecret")
     * 情况四 -> SpEL表达式和非SpEL表达式混合使用
     * ps:  @LogAnnotation(successSpEl = "'登入系统id:'+#loginParam.clientSecret")
     * 情况五 -> 使用获取结果          【注意result代表结果对象，这个是代码里面规定的，使用的时候不能随便定义】
     * ps:  @LogAnnotation(successSpEl = "#result")
     * 情况六 -> 参数和结果混合使用     【注意result代表结果对象，这个是代码里面规定的，使用的时候不能随便定义】
     * ps:  @LogAnnotation(successSpEl = "#loginParam.clientSecret + #result.status")
     * 情况七 -> 使用ip          【注意ip代表ip】
     * ps:  @LogAnnotation(successSpEl = "#ip")
     * 情况八 -> 使用当前时间          【注意now代表当前时间】
     * ps:  @LogAnnotation(successSpEl = "#now")
     */
    String successSpEl() default "'成功'";

    /**
     * 操作失败对应的SpEL表达式
     * 举例子说明：
     * 情况一 -> 使用固定的字符串(tuacy)
     * ps:  @LogAnnotation(errorSpEl = "'tuacy'")
     * 情况二 -> 使用函数的某个参数(name是函数的某个参数)
     * ps:  @LogAnnotation(errorSpEl = "#name")
     * 情况三 -> 使用函数的某个参数的某个属性(name是函数的某个参数)
     * ps:  @LogAnnotation(errorSpEl = "#loginParam.clientSecret")
     * 情况四 -> SpEL表达式和非SpEL表达式混合使用
     * ps:  @LogAnnotation(errorSpEl = "'登入系统id:'+#loginParam.clientSecret")
     * 情况五 -> 使用获取结果          【注意result代表结果对象，这个是代码里面规定的，使用的时候不能随便定义】
     * ps:  @LogAnnotation(errorSpEl = "#result")
     * 情况六 -> 参数和结果混合使用     【注意result代表结果对象，这个是代码里面规定的，使用的时候不能随便定义】
     * ps:  @LogAnnotation(errorSpEl = "#loginParam.clientSecret + #result.status")
     * 情况七 -> 使用ip          【注意ip代表ip】
     * ps:  @LogAnnotation(errorSpEl = "#ip")
     * 情况八 -> 使用当前时间          【注意now代表当前时间】
     * ps:  @LogAnnotation(errorSpEl = "#now")
     * 情况九 -> 使用错误对应的描述信息  【注意errorMessage代表当前时间】
     * ps:  @LogAnnotation(errorSpEl = "#errorMessage")
     */
    String errorSpEl() default "'失败:'+#result.status";

    /**
     * 操作异常对应的SpEL表达式 SpEL表达式里面只能用异常和参数
     * 举例子说明：
     * 情况一 -> 使用固定的字符串(tuacy)
     * ps:  @LogAnnotation(exceptionSpEl = "'tuacy'")
     * 情况二 -> 使用函数的某个参数(name是函数的某个参数)
     * ps:  @LogAnnotation(exceptionSpEl = "#name")
     * 情况三 -> 使用函数的某个参数的某个属性(name是函数的某个参数)
     * ps:  @LogAnnotation(exceptionSpEl = "#loginParam.clientSecret")
     * 情况四 -> SpEL表达式和非SpEL表达式混合使用
     * ps:  @LogAnnotation(exceptionSpEl = "'登入系统id:'+#loginParam.clientSecret")
     * 情况五 -> 使用异常结果          【注意exception代表结果对象，这个是代码里面规定的，使用的时候不能随便定义】
     * ps:  @LogAnnotation(exceptionSpEl = "'异常:'+#exception.detailMessage")
     * 情况六 -> 使用ip          【注意ip代表ip】
     * ps:  @LogAnnotation(exceptionSpEl = "#ip")
     * 情况七 -> 使用当前时间          【注意now代表当前时间】
     * ps:  @LogAnnotation(exceptionSpEl = "#now")
     */
    String exceptionSpEl() default "'异常:'+#exception.message";


    /**
     * 内容生成策略
     */
    Class<? extends LogContentStrategy> contentStrategy() default DefaultLogContentStrategy.class;

    /**
     * 请求是否执行成功的判断策略
     */
    Class<? extends LogSuccessCheckStrategy> successStrategy() default DefaultLogSuccessCheckStrategy.class;

    /**
     * 获取错误描述信息
     */
    Class<? extends LogErrorMessageStrategy> errorMessageStrategy() default DefaultLogErrorMessageStrategy.class;
}
