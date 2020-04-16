package com.tuacy.log.spring.boot.autoconfigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @name: BizLogAutoConfiguration
 * @author: tuacy.
 * @date: 2020/4/13.
 * @version: 1.0
 * @Description:
 */
@Configuration
@Import(
        {
                LogConfigurations.LogBuilderConfiguration.class
        }
)
public class LogAutoConfiguration {
}
