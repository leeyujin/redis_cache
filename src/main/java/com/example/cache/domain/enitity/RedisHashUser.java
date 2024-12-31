package com.example.cache.domain.enitity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter // Getter없으면 해당 객체 조회 시 빈값 나옴
@RedisHash(value = "redishash-user", timeToLive = 30L) // TTL은 HashSet에만 적용되고 자동으로 저장되는 set(SADD ${value} 2)에는 적용안됨 주의
public class RedisHashUser {
    @Id
    private Long id;

    @Indexed
    private String email;

    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
