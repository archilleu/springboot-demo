package com.example.common.cache.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * @author cjy
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfiguration {
    @Value("${cache-prefix:demo}")
    private String cachePrefix;

    final private static String split = ":";

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        class RedisKeySerializer implements RedisSerializer<String> {
            @Override
            public byte[] serialize(String s) throws SerializationException {
                final String prefix = cachePrefix.concat(split);
                return prefix.concat(s).getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String deserialize(byte[] bytes) throws SerializationException {
                final String prefix = cachePrefix.concat(split);
                String key = new String(bytes, StandardCharsets.UTF_8);
                return key.replace(prefix, "");
            }
        }

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        template.setKeySerializer(new RedisKeySerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(new RedisKeySerializer());
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    @Primary
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                // 过期时间
                .entryTtl(Duration.ofHours(16))
                // 禁止空置
                .disableCachingNullValues()
                // 缓存前缀（使用项目名)
                .computePrefixWith(cacheName -> cachePrefix.concat(split)
                        .concat(cacheName).concat(split))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(cacheConfiguration).build();
    }
}
