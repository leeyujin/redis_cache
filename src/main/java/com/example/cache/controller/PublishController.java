package com.example.cache.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.cache.config.RedisConfig.USERS_UNREGISTER;

@RestController
@RequiredArgsConstructor
public class PublishController {
    private final RedisTemplate<String, String> redisTemplate;

    @PostMapping("events/users/deregister")
    void publishUserDeregisterEvent() {
        redisTemplate.convertAndSend(USERS_UNREGISTER, "500");

    }
}
