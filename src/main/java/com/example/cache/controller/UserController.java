package com.example.cache.controller;

import com.example.cache.domain.enitity.RedisHashUser;
import com.example.cache.domain.enitity.User;
import com.example.cache.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/redishash-users/{id}")
    public RedisHashUser getRedisHashUser(@PathVariable Long id) {
        return userService.getRedisHashUser(id);
    }

    @GetMapping("/cache-users/{id}")
    public User getCacheUser(@PathVariable Long id) {
        return userService.getCacheUser(id);
    }
}
