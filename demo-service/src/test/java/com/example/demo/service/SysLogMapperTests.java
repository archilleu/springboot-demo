package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@SpringBootTest
public class SysLogMapperTests {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testInsert() {
        int count = 1000 * 1000;
        Set<ZSetOperations.TypedTuple<Object>> zset = new HashSet<>(count * 2);
        for (int i = 0; i < count; i++) {
            ZSetOperations.TypedTuple<Object> a = new DefaultTypedTuple<>(String.format("%032d", i), i * 0.1d);
            zset.add(a);
        }
        Long start = System.currentTimeMillis();
        redisTemplate.opsForZSet().add("zset", zset);
        System.out.println("==============cost:ms" + (System.currentTimeMillis() - start));
    }

    @Test
    public void testList() {
        int page = 100;
        int count = 1000 * 1000;
        int pages = count / page;

        for (int i = 0; i < pages; i++) {
            long start = System.currentTimeMillis();
            Set<Object> set = redisTemplate.opsForZSet().range("zset", i * page, (i + 1) * page - 1);
            long end = System.currentTimeMillis() - start;
            System.out.println("花费时间:" + end);
            set.forEach(x -> System.out.println(x));
        }
    }
}
