package com.hoya.app.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoya.app.demo.dao.UserDao;
import com.hoya.app.demo.entity.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Override
	public Long rowCount() {
		return userDao.rowCount();
	}

	@Override
	public Long rowCount(String username) {
		return userDao.rowCount(username);
	}

	@Override
	public User getUser(String username) {
		return userDao.getUser(username);
	}

	@Override
	public void saveUser(User user) {
		userDao.save(user);
		
	}

	@Override
	public void updatePassword(User user) {
		userDao.updatePassword(user);
	}

	@Override
	public void delete(User user) {
		userDao.delete(user);
	}

	@Override
	public List<User> getUserList() {
		return userDao.getUserList();
	}

	@Autowired
	private UserDao userDao;

	//抛出runtime exception 事务回滚
	@Override
	public void transaction() throws RuntimeException {
		User user = new User();
		user.setName("transaction");
		user.setPassword("pwd");
		userDao.save(user);

		throw new RuntimeException();
	}
}