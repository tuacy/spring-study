package com.tuacy.redission.lock.spring.boot.autoconfigure;

import com.tuacy.redission.lock.spring.boot.autoconfigure.core.RedissonLockAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @name: RedissonLockAutoConfiguration
 * @author: tuacy.
 * @date: 2020/4/16.
 * @version: 1.0
 * @Description:
 */
@Configuration
public class RedissonLockAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RedissonLockAspect redissonLockAspect() {
        return new RedissonLockAspect();
    }

}
