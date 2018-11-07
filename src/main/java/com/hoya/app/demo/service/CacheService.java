package com.hoya.app.demo.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.hoya.app.demo.entity.User;

@Service
public class CacheService {

	@Cacheable("row")
	public Long getRow() {
		++call;
		return ROW_COUNT;
	}

	@Cacheable("row_")//还可以使用SpEL表达式
	public Long getRow(Long idx) {
		++call;
		return idx;
	}

	@CachePut(cacheNames="user_", key="#user.name") //更新缓存
	public User put(User user) {
		return user;
	}

	@CacheEvict(cacheNames="user_", key="#user.name") //删除缓存
	public void evict(User user) {
		return;
	}
	
	public int getCall() {
		return call;
	}

	public int call = 0;
	public static final Long ROW_COUNT = 1L;
}
