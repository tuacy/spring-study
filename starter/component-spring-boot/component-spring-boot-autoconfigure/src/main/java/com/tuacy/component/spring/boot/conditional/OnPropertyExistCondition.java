package com.tuacy.component.spring.boot.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 21:05
 * 判断配置文件里面指定的属性是否存在
 */
public class OnPropertyExistCondition implements Condition {

    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Map<String, Object> annotationAttributes = annotatedTypeMetadata.getAnnotationAttributes(ConditionalOnPropertyExist.class.getName());
        if (annotationAttributes == null) {
            return false;
        }
        String propertyName = (String) annotationAttributes.get("name");
        boolean values = Boolean.parseBoolean(annotationAttributes.get("exist").toString());
        String propertyValue = conditionContext.getEnvironment().getProperty(propertyName);
        if (values) {
            return !StringUtils.isEmpty(propertyValue);
        } else {
            return StringUtils.isEmpty(propertyValue);
        }
    }

}
