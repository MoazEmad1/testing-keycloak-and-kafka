package com.javatechie.keycloak.kafka;

import com.javatechie.keycloak.entity.FormData;
import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Getter
@Service
public class KafkaConsumer {
    private static final String TOPIC = "test_topic";
    private FormData formData;

    @KafkaListener(topics = TOPIC, groupId = "myGroup")
    public void consumeMessage(ConsumerRecord<String,String> record) {
        String message = record.value();
        String[] parts=message.split(" ");
        String address=parts[0];
        String phone=parts[1];
        formData = new FormData(address, phone);
        System.out.println("Received Kafka message: " + address + " " + phone);
    }

}
