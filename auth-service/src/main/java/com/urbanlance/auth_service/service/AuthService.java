package com.urbanlance.auth_service.service;

import com.urbanlance.auth_service.producer.UserCreatedEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("auth-service.domain")
    private String domain;
    private final UserCreatedEventProducer userCreatedEventProducer;

    public void login(OAuth2User user , OAuth2AuthorizedClient authorizedClient, ServerHttpResponse response){
        ResponseCookie accessToken = ResponseCookie.from("accesstoken",authorizedClient.getAccessToken().toString())
                .path("/")
                .domain(domain)
                .httpOnly(true)
                .secure(true)
                .build();
        response.getHeaders().add("Set-Cookie",accessToken.toString());
        ResponseCookie refreshToken = ResponseCookie.from("refreshtoken",authorizedClient.getRefreshToken().toString())
                .path("/")
                .domain(domain)
                .httpOnly(true)
                .secure(true)
                .build();
        response.getHeaders().add("Set-Cookie",refreshToken.toString());
        String email = user.getAttribute("email");
        String name = user.getName();
        String preferredName = user.getAttribute("preferred_username");
        userCreatedEventProducer.send(email,name,preferredName);
    }
}
