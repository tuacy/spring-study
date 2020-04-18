package com.tuacy.component.spring.boot.autoconfigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 21:05
 */
@Configuration
@Import(
        {
                BeanConfigurations.PropertyConditionBuilderConfiguration.class,
                BeanConfigurations.ExceptionHandlerBuilderConfiguration.class,
                BeanConfigurations.ValidatorBuilderConfiguration.class,
                BeanConfigurations.RedisBuilderConfiguration.class
        }
)
public class BeanAutoConfiguration {
}
