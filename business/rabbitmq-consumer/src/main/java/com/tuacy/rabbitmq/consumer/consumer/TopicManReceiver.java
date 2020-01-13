package com.tuacy.rabbitmq.consumer.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @name: TopicManReceiver
 * @author: tuacy.
 * @date: 2020/1/13.
 * @version: 1.0
 * @Description:
 */
@Component
@RabbitListener(queues = "topic.man")
public class TopicManReceiver {

    @RabbitHandler
    public void process(Map message) {
        System.out.println("TopicManReceiver消费者收到消息  : " + message.toString());
    }

}
