package com.urbanlance.auth_service.config;

import com.urbanlance.auth_service.oauth2.CookieBearerTokenConverter;
import com.urbanlance.auth_service.oauth2.Oauth2SuccessHandler;
import com.urbanlance.auth_service.repository.RedisAuthorizationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final Oauth2SuccessHandler successHandler;
    private final CookieBearerTokenConverter cookieBearerTokenConverter;
    private final RedisAuthorizationRequestRepository redisAuthorizationRequestRepository;

    @Bean
    public SecurityWebFilterChain asFilterChain(ServerHttpSecurity http){
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        http.authorizeExchange(auth -> auth
                .anyExchange().authenticated()
        );


        http.oauth2Login(oauth -> oauth
                        .authenticationConverter(cookieBearerTokenConverter)
                        .authenticationSuccessHandler(successHandler)
                        .authorizationRequestRepository(redisAuthorizationRequestRepository)
                );
        http.oauth2ResourceServer(resource -> resource
                    .bearerTokenConverter(cookieBearerTokenConverter)
                    .jwt(Customizer.withDefaults())
                );
        http.oauth2Client(client -> client
                .authorizationRequestRepository(redisAuthorizationRequestRepository)
                .authenticationConverter(cookieBearerTokenConverter)
        );

//        http.securityContextRepository(NoOpServerSecurityContextRepository.getInstance());
        return http.build();
    }
}
