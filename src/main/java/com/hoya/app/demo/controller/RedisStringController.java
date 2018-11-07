package com.hoya.app.demo.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoya.app.demo.entity.User;
import com.hoya.app.demo.service.CacheService;

@Controller
@RequestMapping("/redis")
public class RedisStringController {

	@RequestMapping("/setget.html")
	@ResponseBody
	public String env(String param) throws Exception {
		redisClient.opsForValue().set("testenv",  param);
		String str = redisClient.opsForValue().get("testenv");
		return str;
	}

	@RequestMapping("/user.json")
	@ResponseBody
	public User User() throws Exception {
		cache.getRow();
		User user = new User("name", "password", "ingore");
		jsonRedisTemplate.opsForValue().set("user", user);
		jsonRedisTemplate.opsForValue().set("user1", user);
		jsonRedisTemplate.opsForValue().set("count", 1L);
		user = (User) jsonRedisTemplate.opsForValue().get("user");
		return user;
	}
	
	@Autowired
	private StringRedisTemplate redisClient;
	
	@Resource(name="jsonRedisTemplate")
	private RedisTemplate<String, Object> jsonRedisTemplate;
	
	@Autowired
	CacheService cache;
}
