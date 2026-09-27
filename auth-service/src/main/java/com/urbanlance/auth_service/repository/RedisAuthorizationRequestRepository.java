package com.urbanlance.auth_service.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.security.oauth2.client.web.server.ServerAuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class RedisAuthorizationRequestRepository implements
        ServerAuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private final ReactiveStringRedisTemplate redisTemplate;
    private static Duration TTL = Duration.ofMinutes(5);
    private static final String key = "oauth2:authorizationRequest";

    @Override
    public Mono<OAuth2AuthorizationRequest> loadAuthorizationRequest(ServerWebExchange exchange) {
        ObjectMapper mapper = new ObjectMapper();
        Mono<String> jsonString = redisTemplate.opsForValue()
                .get(key);
        return jsonString.flatMap(request ->{
            OAuth2AuthorizationRequest oAuth2AuthorizationRequest =
                    mapper.readValue(request,OAuth2AuthorizationRequest.class);
            return Mono.just(oAuth2AuthorizationRequest);
        });
    }

    @Override
    public Mono<Void> saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, ServerWebExchange exchange) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(authorizationRequest);
        redisTemplate.opsForValue()
                .set(key,jsonString,TTL);
        return Mono.empty();
    }

    @Override
    public Mono<OAuth2AuthorizationRequest> removeAuthorizationRequest(ServerWebExchange exchange) {
        ObjectMapper mapper = new ObjectMapper();
        Mono<String> jsonString = redisTemplate.opsForValue()
                        .get(key);
        Mono<OAuth2AuthorizationRequest> oAuth2AuthorizationRequestMono = jsonString.flatMap(request ->{
            OAuth2AuthorizationRequest oAuth2AuthorizationRequest =
                    mapper.readValue(request,OAuth2AuthorizationRequest.class);
            return Mono.just(oAuth2AuthorizationRequest);
        });
        redisTemplate.opsForValue()
                .delete(key);
        return oAuth2AuthorizationRequestMono;
    }
}
