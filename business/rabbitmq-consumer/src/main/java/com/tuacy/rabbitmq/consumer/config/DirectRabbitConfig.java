package com.tuacy.rabbitmq.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @name: DirectRabbitConfig
 * @author: tuacy.
 * @date: 2020/1/13.
 * @version: 1.0
 * @Description:
 */
@Configuration
public class DirectRabbitConfig {

    /**
     * 队列 起名：TestDirectQueue
     */
    @Bean
    public Queue TestDirectQueue() {
        return new Queue("TestDirectQueue", true);  //true 是否持久
    }

    /**
     * Direct交换机 起名：TestDirectExchange
     */
    @Bean
    DirectExchange TestDirectExchange() {
        return new DirectExchange("TestDirectExchange");
    }

    /**
     * 绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
     */
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(TestDirectQueue()).to(TestDirectExchange()).with("TestDirectRouting");
    }

}
