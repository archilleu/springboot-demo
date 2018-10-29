package com.hoya.app.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoya.app.demo.entity.User;
import com.hoya.app.demo.repository.UserRepository;

@Service
@Transactional
public class UserRepositoryServiceImpl implements UserService {

	@Override
	public Long rowCount() {
		return repository.count();
	}

	@Override
	public Long rowCount(String username) {
		// TODO Auto-generated method stub
		return repository.count();
	}

	@Override
	public User getUser(String username) {
		try {
			return repository.findById(username).get();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void saveUser(User user) {
		repository.save(user);
	}

	@Override
	public void updatePassword(User user) {
		repository.save(user);
	}

	@Override
	public void delete(User user) {
		try {
		repository.delete(user);
		} catch (Exception e) {
			return;
		}
	}

	@Override
	public List<User> getUserList() {
		return repository.findAll();
	}
	
	public List<User> getUserListSortAndPage() {
		Sort sort = new Sort(Direction.DESC, "name");
		PageRequest pageable = PageRequest.of(0, 1, sort);
		Page<User> pageObject = repository.findAll(pageable);
		return pageObject.getContent();
	}
	
	public List<User> getUserListBySql() {
		return repository.getListBySql();
	}
	
	public void updateUserBySql(String name, String password) {
		repository.updateUserBySql(name, password);
	}
	

	@Override
	public void transaction() throws RuntimeException {
		User user = new User();
		user.setName("transaction");
		user.setPassword("pwd");
		repository.save(user);

		throw new RuntimeException();
		
	}

	@Autowired
	UserRepository repository;
}
