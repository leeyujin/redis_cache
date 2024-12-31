package com.example.jediscache;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final JedisPool jedisPool;

    @GetMapping("/users/{id}/email")
    public String getUserEmail(@PathVariable Long id) {
        try(Jedis jedis = jedisPool.getResource()) {
            // 1. request to cache
            String key = "users:%d:email".formatted(id);
            String userEmail = jedis.get(key);
            if(userEmail != null){
                return userEmail;
            }
            // 2. else db
            userEmail = userRepository.findById(id).orElse(User.builder().build()).getEmail();

            // 3. cache
            jedis.setex(key, 30, userEmail);
            return userEmail;
        }
    }
}
