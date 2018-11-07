package com.hoya.app.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoya.app.demo.dao.UserDao;

@Service
public class MockService {

	//mock 模拟接口，mock注入UserDao
	public Long mockTest() {
		return userDao.rowCount();
	}

	@Autowired
	private UserDao userDao;
}
