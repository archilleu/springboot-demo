package com.example.api.controller;

import com.alibaba.fastjson.JSON;
import com.example.api.model.entity.TestStream;
import com.example.common.base.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cjy
 */
@Slf4j
@RestController
@RequestMapping("/kafka")
public class KafkaController {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Log
    @GetMapping("/send")
    public void send(String topicName, String message) {
        kafkaTemplate.send(topicName, message);
    }

    @Log
    @GetMapping("/send-user")
    public void send(String topicName, TestStream message) {
        kafkaTemplate.send(topicName, JSON.toJSONString(message));
    }
}
