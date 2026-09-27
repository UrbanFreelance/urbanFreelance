package com.urbanlance.user.mapper;

import com.urbanlance.common.events.UserCreatedEvent;
import com.urbanlance.user.model.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public Profile toEntity(UserCreatedEvent event){
        return new Profile(
                event.keycloakUserId(),
                event.email(),
                event.fullName(),
                event.preferredName()
                );
    }
}
