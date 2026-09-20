package com.urbanlance.auth_service.controller;

import com.urbanlance.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("urbanlance.com/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public void login(@AuthenticationPrincipal OAuth2User oAuth2User,
                      @RegisteredOAuth2AuthorizedClient("keycloak")OAuth2AuthorizedClient authorizedClient,
                      ServerHttpResponse response
    ){

    }

    public void register(){

    }
}
