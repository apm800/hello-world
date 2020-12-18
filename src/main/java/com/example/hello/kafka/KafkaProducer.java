package com.example.hello.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author zk
 * @date 2020/11/25 9:50
 */
@Component
@Slf4j
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "hello";

    public void sendKafkaMsg(String msg) {
        kafkaTemplate.send(TOPIC, msg);
        log.info("已发送kafka消息:" + msg);
    }
}
