package com.urbanlance.common.events;


public record UserCreatedEvent(
        String keycloakUserId,
        String email,
        String preferredName,
        String fullName
) {
}
