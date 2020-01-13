package com.tuacy.rabbitmq.provider;

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
public class RabbitMQProviderServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMQProviderServerApplication.class, args);
    }

}
