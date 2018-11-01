package com.hoya.app.demo.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoya.app.demo.entity.User;

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
		User user = new User("name", "password", "ingore");
		jsonRedisTemplate.opsForValue().set("user", user);
		user = jsonRedisTemplate.opsForValue().get("user");
		return user;
	}
	
	@Autowired
	private StringRedisTemplate redisClient;
	
	@Resource
	private RedisTemplate<String, User> jsonRedisTemplate;
}
