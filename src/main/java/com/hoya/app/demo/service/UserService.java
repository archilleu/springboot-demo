package com.hoya.app.demo.service;

import java.util.List;

import com.hoya.app.demo.entity.User;

public interface UserService {
	
	public void clear();
	public Long rowCount();
	public Long rowCount(String username); 
	public User getUser(String username);
	public void saveUser(User user);
	public User updatePassword(User user);
	public void delete(User user);
	public List<User> getUserList();

	public void transaction() throws RuntimeException;
}
