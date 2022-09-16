package com.example.api.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author cjy
 */
@Slf4j
@Component
public class MessageConsumer {
    @KafkaListener(topics = "hello", groupId = "defaultGroup")
    public void hello(ConsumerRecord<String, Object> record, Acknowledgment ack) {
        log.info("topic:{},partition:{},value:{}", record.topic(), record.partition(), record.value());
        ack.acknowledge();
    }

//    /**
//     * 配置多个消费组
//     * @param record
//     */
//    @KafkaListener(topics = "hello", groupId = "defaultGroup2")
//    public void hello2(ConsumerRecord<?, ?> record){
//        System.out.println("简单消费2："+record.topic()+"-"+record.partition()+"-"+record.value());
//    }

    @KafkaListener(topics = "user", groupId = "defaultGroup")
    public void user(ConsumerRecord<String, String> record, Acknowledgment ack) {
        log.info("topic:{},partition:{},value:{}", record.topic(), record.partition(), record.value());
        ack.acknowledge();
    }
}
