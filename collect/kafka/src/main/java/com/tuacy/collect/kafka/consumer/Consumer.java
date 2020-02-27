package com.tuacy.collect.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @name: Consumer
 * @author: tuacy.
 * @date: 2020/2/27.
 * @version: 1.0
 * @Description:
 */
@Component
public class Consumer {

    @KafkaListener(topics = "test_topic")
    public void listen(ConsumerRecord<?, ?> consumerRecord, Acknowledgment ack) {
        Optional<Object> kafkaMassage = Optional.ofNullable(consumerRecord.value());
        if (kafkaMassage.isPresent()) {
            ack.acknowledge();
        }

    }

}
