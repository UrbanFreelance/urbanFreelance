package com.urbanlance.auth_service.oauth2;


import com.urbanlance.auth_service.producer.UserCreatedEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


import java.util.Map;

@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler implements ServerAuthenticationSuccessHandler {

    @Value("auth-service.domain")
    private String domain;
    private final UserCreatedEventProducer userCreatedEventProducer;
    private final ServerOAuth2AuthorizedClientRepository clientRepository;
    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {

        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        OAuth2AuthenticationToken oauth = (OAuth2AuthenticationToken) authentication;
        Map<String,Object> attr = user.getAttributes();
        ServerWebExchange exchange = webFilterExchange.getExchange();

        String registrationId = oauth.getAuthorizedClientRegistrationId();

        return clientRepository.loadAuthorizedClient(
                registrationId,
                authentication,
                webFilterExchange.getExchange()
        )
                .flatMap(client -> {
                    OAuth2AccessToken accessToken = client.getAccessToken();
                    OAuth2RefreshToken refreshToken = client.getRefreshToken();
                    ResponseCookie accessTokenCookie = ResponseCookie.from("access_token",accessToken.getTokenValue())
                            .path("/")
                            .domain(domain)
                            .httpOnly(true)
                            .secure(true)
                            .build();
                    exchange.getResponse().addCookie(accessTokenCookie);
                    if(refreshToken != null){
                        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh_token",refreshToken.getTokenValue())
                                .path("/")
                                .domain(domain)
                                .httpOnly(true)
                                .secure(true)
                                .build();
                        exchange.getResponse().addCookie(refreshTokenCookie);
                    }
                    String email = (String) attr.get("email");
                    String name = user.getName();
                    String preferredName = (String) attr.get("preferred_username");
                    userCreatedEventProducer.send(email,preferredName,name);
                    return exchange.getResponse().setComplete();
                });
    }
}
