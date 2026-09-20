package com.urbanlance.auth_service.oauth2;

import org.springframework.http.HttpCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.web.server.authentication.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


public class CookieBearerTokenConverter implements ServerAuthenticationConverter {

    ServerBearerTokenAuthenticationConverter delegate =
            new ServerBearerTokenAuthenticationConverter();

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        HttpCookie cookie = exchange.getRequest()
                .getCookies()
                .getFirst("access_token");

        if(cookie != null && !cookie.getValue().isBlank()){
            return Mono.just(
                    new BearerTokenAuthenticationToken(
                            cookie.getValue()
                    )
            );
        }

        return delegate.convert(exchange);
    }
}
