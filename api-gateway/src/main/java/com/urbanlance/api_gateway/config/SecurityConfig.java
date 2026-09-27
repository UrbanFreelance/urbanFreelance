package com.urbanlance.api_gateway.config;

import com.urbanlance.api_gateway.oauth2.CookieBearerTokenConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("auth.jet.public.key")
    private RSAPublicKey publicKey;
    private final CookieBearerTokenConverter cookieBearerTokenConverter;

    public SecurityConfig(CookieBearerTokenConverter cookieBearerTokenConverter) {
        this.cookieBearerTokenConverter = cookieBearerTokenConverter;
    }

    @Bean
    public SecurityWebFilterChain asFilterChain(ServerHttpSecurity http){
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);

        http.securityContextRepository(NoOpServerSecurityContextRepository.getInstance());
        http.authorizeExchange(auth -> auth
                .anyExchange()
                .authenticated()
        );

        http.oauth2ResourceServer(resource -> resource
                .jwt(jwtSpec -> jwtSpec.jwtDecoder(jwtDecoder()))
                .bearerTokenConverter(cookieBearerTokenConverter)
        );
        return http.build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder(){
        return NimbusReactiveJwtDecoder.withPublicKey(publicKey)
                .build();
    }
}
