package com.tuacy.rabbitmq.consumer.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @name: TopicTotalReceiver
 * @author: tuacy.
 * @date: 2020/1/13.
 * @version: 1.0
 * @Description:
 */
@Component
@RabbitListener(queues = "topic.woman")
public class TopicTotalReceiver {

    @RabbitHandler
    public void process(Map message) {
        System.out.println("TopicTotalReceiver消费者收到消息  : " + message.toString());
    }

}
