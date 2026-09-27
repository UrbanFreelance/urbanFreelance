package com.urbanlance.api_gateway.oauth2;

import org.springframework.http.HttpCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.web.server.authentication.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class CookieBearerTokenConverter implements ServerAuthenticationConverter {

    private ServerBearerTokenAuthenticationConverter delegate = new ServerBearerTokenAuthenticationConverter();
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
         HttpCookie access_token = exchange.getRequest()
                .getCookies()
                .getFirst("access_token");
        if(access_token != null && !access_token.getValue().isBlank()){
            return Mono.just(
                    new BearerTokenAuthenticationToken(
                            access_token.getValue()
                    )
            );
        }
        return delegate.convert(exchange);
    }
}
