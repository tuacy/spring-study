package com.tuacy.web.log.spring.boot.autoconfigure;

import com.tuacy.web.log.spring.boot.aop.BizLogAspect;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @name: BizLogAutoConfiguration
 * @author: tuacy.
 * @date: 2020/4/13.
 * @version: 1.0
 * @Description:
 */
@Configuration
@AutoConfigureAfter(JdbcTemplateAutoConfiguration.class)
public class BizLogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(BizLogAspect.class)
    public BizLogAspect bizLogAspect(JdbcTemplate jdbcTemplate) {
        return new BizLogAspect(jdbcTemplate);
    }

}
