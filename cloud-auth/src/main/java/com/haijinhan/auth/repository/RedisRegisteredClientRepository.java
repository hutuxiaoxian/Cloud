package com.haijinhan.auth.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRegisteredClientRepository implements RegisteredClientRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String KEY_PREFIX = "oauth:client:";

    public RedisRegisteredClientRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        redisTemplate.opsForValue().set(KEY_PREFIX + registeredClient.getClientId(), registeredClient);
    }

    @Override
    public RegisteredClient findById(String id) {
        // 如果 Redis 按 clientId 存储，直接根据 clientId 获取，id 就是 clientId
        return (RegisteredClient) redisTemplate.opsForValue().get(KEY_PREFIX + id);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return (RegisteredClient) redisTemplate.opsForValue().get(KEY_PREFIX + clientId);
    }
}