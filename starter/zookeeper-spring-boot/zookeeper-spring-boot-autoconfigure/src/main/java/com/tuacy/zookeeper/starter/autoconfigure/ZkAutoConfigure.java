package com.tuacy.zookeeper.starter.autoconfigure;

import com.tuacy.zookeeper.starter.core.ZkClientTemplate;
import com.tuacy.zookeeper.starter.core.configure.ZkProperties;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/3/29 11:20
 */
@Configurable
@EnableConfigurationProperties(ZkProperties.class)
public class ZkAutoConfigure {

    private final ZkProperties zkProperties;

    public ZkAutoConfigure(ZkProperties zkProperties) {
        this.zkProperties = zkProperties;
    }

    @Bean()
    @ConditionalOnClass(ZkClientTemplate.class)
    @ConditionalOnMissingBean()
    public ZkClientTemplate zkClientTemplate() {
        return new ZkClientTemplate(zkProperties);
    }

}
