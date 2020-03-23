package com.tuacy.component.spring.boot.autoconfigure;

import com.tuacy.component.spring.boot.advice.ResponseExceptionsHandler;
import com.tuacy.component.spring.boot.conditional.OnPropertyExistCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/21 21:05
 */
@Configuration
public class BeanAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(OnPropertyExistCondition.class)
    public OnPropertyExistCondition onPropertyExistCondition() {
        return new OnPropertyExistCondition();
    }

    @Bean
    @ConditionalOnMissingBean(ResponseExceptionsHandler.class)
    public ResponseExceptionsHandler responseExceptionsHandler() {
        return new ResponseExceptionsHandler();
    }

}
