package com.urbanlance.auth_service.service;

import com.urbanlance.auth_service.producer.UserCreatedEventProducer;
import jakarta.ws.rs.core.UriBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jackson.autoconfigure.JacksonProperties;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("auth-service.domain")
    private String domain;
    private final UserCreatedEventProducer userCreatedEventProducer;
    private final WebClient webClient;
    @Value("keycloak.auth-service-uri")
    private String serverUri;
    @Value("keycloak.realm")
    private String realm;
    @Value("keycloak.resource")
    private String clientId;
    @Value("keycloak.credentials.secret")
    private String clientSecret;

    public void createUserProfile(OAuth2User user , OAuth2AuthorizedClient authorizedClient){
        String keycloakUserId = user.getAttribute("sub");
        String email = user.getAttribute("email");
        String name = user.getName();
        String preferredName = user.getAttribute("preferred_username");
        userCreatedEventProducer.send(keycloakUserId,email,name,preferredName);
    }

    public Mono<JsonNode> refreshToken(String refreshToken){

        String url = serverUri + "/realms/"+realm + "/protocol/openid-connect/token";
        return webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("client_id",clientId)
                        .with("client_secret",clientSecret)
                        .with("grant_type","refresh_token")
                        .with("refresh_token",refreshToken)
                )
                .retrieve()
                .bodyToMono(JsonNode.class);
    }
}
