package com.example.cache.service;

import com.example.cache.domain.enitity.RedisHashUser;
import com.example.cache.domain.enitity.User;
import com.example.cache.repository.RedisHashUserRepository;
import com.example.cache.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static com.example.cache.config.CacheConfig.CACHE1;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> objectRedisTemplate;
    private final RedisHashUserRepository redisHashUserRepository;

    public User getUser(final Long id) {
        // 1. cache get
        String key = "users:%d".formatted(id);
        User cachedUser = (User) objectRedisTemplate.opsForValue().get(key);
        if (cachedUser != null) {
            return cachedUser;
        }

        // 2. else db get -> cache set
        User user = userRepository.findById(id).orElseThrow();
        objectRedisTemplate.opsForValue().set(key, user, Duration.ofSeconds(30));

        return user;
    }

    public RedisHashUser getRedisHashUser(final Long id) {
        RedisHashUser cachedUser = redisHashUserRepository.findById(id)
                .orElseGet(() -> {
                    User user = userRepository.findById(id).orElseThrow();
                    return redisHashUserRepository.save(
                            RedisHashUser.builder()
                                    .id(user.getId())
                                    .name(user.getName())
                                    .email(user.getEmail())
                                    .createdAt(user.getCreatedAt())
                                    .updatedAt(user.getUpdatedAt())
                                    .build());
                });
        return cachedUser;
    }

    @Cacheable(cacheNames = CACHE1, key = "'user:' + #id")
    public User getCacheUser(final Long id){
        return userRepository.findById(id).orElseThrow();
    }
}
