package com.urbanlance.api_gateway;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class yooController {

    @GetMapping("/hello")
    public Mono<String> hello(@AuthenticationPrincipal OAuth2User user){
        return Mono.just(user.getAttributes().toString());
    }

    @GetMapping("/token")
    public Mono<String> token(
            @RegisteredOAuth2AuthorizedClient("keycloak")
            OAuth2AuthorizedClient client) {

        return Mono.just(client.getAccessToken().getTokenValue());
    }
}
