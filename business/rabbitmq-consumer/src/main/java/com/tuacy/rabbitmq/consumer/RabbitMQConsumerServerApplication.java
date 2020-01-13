package com.tuacy.rabbitmq.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @name: RabbitMQConsumerServcerApplication
 * @author: tuacy.
 * @date: 2020/1/13.
 * @version: 1.0
 * @Description:
 */
@SpringBootApplication
public class RabbitMQConsumerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMQConsumerServerApplication.class, args);
    }

}
