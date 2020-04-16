package com.tuacy.component.spring.boot.autoconfigure;

import com.tuacy.component.spring.boot.advice.ResponseExceptionsHandler;
import com.tuacy.component.spring.boot.conditional.OnPropertyExistCondition;
import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @name: BeanConfigurations
 * @author: tuacy.
 * @date: 2020/4/16.
 * @version: 1.0
 * @Description:
 */
class BeanConfigurations {


    @Configuration(proxyBeanMethods = false)
    static class PropertyConditionBuilderConfiguration {

        @Bean
        @ConditionalOnMissingBean()
        public OnPropertyExistCondition onPropertyExistCondition() {
            return new OnPropertyExistCondition();
        }

    }

    @Configuration(proxyBeanMethods = false)
    static class ExceptionHandlerBuilderConfiguration {


        @Bean
        @ConditionalOnMissingBean()
        public ResponseExceptionsHandler responseExceptionsHandler() {
            return new ResponseExceptionsHandler();
        }

    }

    @Configuration(proxyBeanMethods = false)
    static class ValidatorBuilderConfiguration {


        /**
         * 参数校验相关，快速失败模式: 只要有一个验证失败，则返回
         */
        @Bean
//        @ConditionalOnMissingBean()
        public Validator validator() {
            ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                    .configure()
                    .failFast(true)
                    .buildValidatorFactory();

            return validatorFactory.getValidator();
        }

    }

}
