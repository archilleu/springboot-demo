package com.example.common.cache.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author cjy
 */
@Slf4j
@Component
@SuppressWarnings("unused,unchecked")
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean set(String key, Object value) {
        if (ObjectUtils.isEmpty(key)) {
            return false;
        }

        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("操作redis失败,key[{}], msg[{}]", key, e.getLocalizedMessage());
            return false;
        }
    }

    public boolean set(String key, Object value, int time) {
        if (ObjectUtils.isEmpty(key)) {
            return false;
        }

        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
                return true;
            } else {
                return set(key, value);
            }
        } catch (Exception e) {
            log.error("操作redis失败,key[{}], msg[{}]", key, e.getLocalizedMessage());
            return false;
        }
    }

    public <T> T get(String key) {
        if (ObjectUtils.isEmpty(key)) {
            return null;
        }

        try {
            Object value = redisTemplate.opsForValue().get(key);
            return (T) value;
        } catch (Exception e) {
            log.error("操作redis失败,key[{}], msg[{}]", key, e.getLocalizedMessage());
            return null;
        }
    }

    public <T> List<T> getList(String key) {
        if (ObjectUtils.isEmpty(key)) {
            return null;
        }

        try {
            Object value = redisTemplate.opsForValue().get(key);
            return (List<T>) value;
        } catch (Exception e) {
            log.error("操作redis失败,key[{}], msg[{}]", key, e.getLocalizedMessage());
            return null;
        }
    }

    public void delete(String... key) {
        try {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Arrays.asList(key));
            }
        } catch (Exception e) {
            log.error("操作redis失败,key[{}], msg[{}]", key, e.getLocalizedMessage());
        }
    }
}
