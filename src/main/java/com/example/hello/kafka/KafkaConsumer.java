package com.example.hello.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zk
 * @date 2020/11/25 9:49
 */
@Component
@PropertySource("/application.properties")
@Slf4j
public class KafkaConsumer {

    @Value("${spring.profiles.active}")
    private String environment;

    public static String msg;

    private Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>(6);
        String pro = "pro";
        if (pro.equals(environment)) {
            //生产(多个ip用','分开)
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "47.94.99.1:9092");
        } else {
            //测试(多个ip用','分开)
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "47.94.99.1:9092");
        }
        //不自动消费,改成手动消费信息
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        //以下为加密认证时使用
//        props.put("security.protocol", "SASL_PLAINTEXT");
//        props.put("sasl.mechanism", "PLAIN");
//        props.put("sasl.jaas.config", "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"账号\" password=\"密码\";");

        return props;
    }

    @Bean("ackContainerFactory")
    public ConcurrentKafkaListenerContainerFactory ackContainerFactory() {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory(consumerProps()));
        return factory;
    }

//    @KafkaListener(id = "*", topics = "#{'${spring.topics}'.split(',')}", containerFactory = "ackContainerFactory")
//    public void listen(ConsumerRecord<?, ?> record, Acknowledgment ack) {
//
//        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
//
//        if (kafkaMessage.isPresent()) {
//            String message = kafkaMessage.get().toString();
//            log.info("消费者收到 Message :{}", message);
//            msg = message;
//            ack.acknowledge();
//            log.info("=== 已消费信息 ===");
//        }
//    }

    public String getMsg() {
        return msg;
    }

}
