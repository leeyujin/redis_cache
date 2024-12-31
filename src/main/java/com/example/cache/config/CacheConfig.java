package com.example.cache.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.List;

@EnableCaching
@Configuration
public class CacheConfig {
    public static final String CACHE1 = "cache1";
    public static final String CACHE2 = "cache2";

    @AllArgsConstructor
    @Getter
    public static class CacheProperty {
        private String name;
        private Integer ttl;
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer(ObjectMapper redisObjectMapper) {

        List<CacheProperty> properties = List.of(
                new CacheProperty(CACHE1, 300),
                new CacheProperty(CACHE2, 30)
        );
        return (builder -> {
            properties.forEach(i -> {
                builder.withCacheConfiguration(i.getName(), RedisCacheConfiguration
                        .defaultCacheConfig()
                        .disableCachingNullValues()
                        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(redisObjectMapper)))
                        .entryTtl(Duration.ofSeconds(i.getTtl())));
            });
        });
    }
}
