package com.tuacy.log.spring.boot.autoconfigure;

import com.tuacy.log.spring.boot.core.LogAspect;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @name: LogConfigurations
 * @author: tuacy.
 * @date: 2020/4/16.
 * @version: 1.0
 * @Description:
 */
class LogConfigurations {

    @Configuration(proxyBeanMethods = false)
    @AutoConfigureAfter({JdbcTemplateAutoConfiguration.class})
    static class LogBuilderConfiguration {

        @Bean
        @ConditionalOnMissingBean()
        @ConditionalOnBean(JdbcTemplate.class)
        public LogAspect bizLogAspect(JdbcTemplate jdbcTemplate) {
            return new LogAspect(jdbcTemplate);
        }


    }

}
