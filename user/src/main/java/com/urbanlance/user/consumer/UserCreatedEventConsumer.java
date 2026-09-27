package com.urbanlance.user.consumer;

import com.urbanlance.common.events.UserCreatedEvent;
import com.urbanlance.user.mapper.ProfileMapper;
import com.urbanlance.user.model.Profile;
import com.urbanlance.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreatedEventConsumer {

    private final UserProfileRepository userProfileRepository;
    private final ProfileMapper profileMapper;

    @KafkaListener(topics = {"USER_TOPIC"},groupId = "user")
    public void poll(ConsumerRecord<String, UserCreatedEvent> record){
        UserCreatedEvent userCreatedEvent = record.value();
        Profile profile = profileMapper.toEntity(userCreatedEvent);
        userProfileRepository.save(profile);
    }
}
