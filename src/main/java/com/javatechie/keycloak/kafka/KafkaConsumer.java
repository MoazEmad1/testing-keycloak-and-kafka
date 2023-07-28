package com.javatechie.keycloak.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private static final String TOPIC = "test_topic";
    @KafkaListener(topics = TOPIC, groupId = "group_id")
    public void consumeMessage(ConsumerRecord<String,String> record) {
        String message = record.value();

        System.out.println("Consumed message: " + message);
    }
}
