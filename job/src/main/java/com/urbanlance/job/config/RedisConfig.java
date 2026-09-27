package com.urbanlance.job.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class RedisConfig {

    public RedisConnectionFactory redisConnectionFactory(){
        return new LettuceConnectionFactory();
    }

}
