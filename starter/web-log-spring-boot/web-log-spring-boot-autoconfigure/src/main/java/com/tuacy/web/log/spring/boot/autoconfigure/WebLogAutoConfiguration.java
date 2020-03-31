package com.tuacy.web.log.spring.boot.autoconfigure;

import com.tuacy.web.log.spring.boot.aop.WebLogAspect;
import com.tuacy.web.log.spring.boot.store.DefaultLoggerWebLogStore;
import com.tuacy.web.log.spring.boot.store.IWebLogStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @name: WebLogAutoConfiguration
 * @author: tuacy.
 * @date: 2020/3/24.
 * @version: 1.0
 * @Description:
 */
@Configuration
public class WebLogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(WebLogAspect.class)
    public WebLogAspect webLogAspect() {
        return new WebLogAspect();
    }

    @Bean
    @ConditionalOnMissingBean(IWebLogStore.class)
    public IWebLogStore webLogStore() {
        return new DefaultLoggerWebLogStore();
    }

}