package com.urbanlance.auth_service.controller;

import com.urbanlance.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.pqc.jcajce.provider.uov.SignatureSpi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("urbanlance.com/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @Value("auth-service.domain")
    private String domain;

    @GetMapping("/refresh")
    public Mono<Void> accessToken(ServerWebExchange exchange){
        String refreshToken = exchange.getRequest()
                .getCookies()
                .getFirst("refresh_token")
                .getValue();
        if(refreshToken == null || refreshToken.isBlank()){

        }
        return authService.refreshToken(refreshToken)
                .flatMap(tokens -> {
                    String accessToken = tokens.get("access_token").asString();
                    String newRefreshToken = tokens.get("refresh_token").asString();

                    ResponseCookie accessTokenCookie = ResponseCookie.from("access_token",accessToken)
                            .path("/")
                            .domain(domain)
                            .httpOnly(true)
                            .secure(true)
                            .build();
                    exchange.getResponse().addCookie(accessTokenCookie);
                    if(refreshToken != null) {
                        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh_token", refreshToken)
                                .path("/")
                                .domain(domain)
                                .httpOnly(true)
                                .secure(true)
                                .build();
                        exchange.getResponse().addCookie(refreshTokenCookie);
                    }
                    return Mono.empty();
                });
    }

    @PostMapping("/login")
    public Mono<Void> login(@AuthenticationPrincipal OAuth2User oAuth2User,
                      @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient){
        return Mono.empty();
    }

    @PostMapping("/register")
    public Mono<Void> register(@AuthenticationPrincipal OAuth2User oAuth2User,
                                         @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient){
        authService.createUserProfile(oAuth2User,authorizedClient);
        return Mono.empty();
    }


}
