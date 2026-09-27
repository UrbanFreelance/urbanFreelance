package com.urbanlance.auth_service.producer;

import com.urbanlance.common.events.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class UserCreatedEventProducer {

    private final RoutingKafkaTemplate kafkaTemplate;

    public void send(String id , String email,String preferredName,String fullName){
        UserCreatedEvent request = new UserCreatedEvent(
               id, email,preferredName,fullName);

        ProducerRecord<Object,Object> producerRecord = new ProducerRecord<>(
                "USER_TOPIC",
                email,
                request
        );

        CompletableFuture<SendResult<Object,Object>> future = kafkaTemplate.send(producerRecord);

        future.whenComplete((result,exep)->{

        });
    }
}
