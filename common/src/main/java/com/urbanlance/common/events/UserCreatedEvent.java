package com.urbanlance.common.events;


public record UserCreatedEvent(
        String email,
        String preferredName,
        String fullName
) {
}
