package com.hoya.app.demo.service;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hoya.app.demo.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryServiceTest {
	
	@Test
	public void testRowCount() {
		Long count = userService.rowCount();
		System.out.println("count * : " + count);
		count = userService.rowCount("cjy");
		System.out.println("count cjy : " + count);
		
		User user = userService.getUser("cjy");
		System.out.println(user);
		user = userService.getUser("none");
		assertNull(user);
		
		user = new User();
		user.setName("new user");
		user.setPassword("password");
		userService.saveUser(user);
		
		user.setPassword("new password");
		userService.updatePassword(user);
		
		List<User> users = userService.getUserList();
		System.out.println(users);
		users = userService.getUserListSortAndPage();
		System.out.println(users);
		
		user.setName("new user");
		userService.delete(user);
		assertNull(userService.getUser("new user"));
		
		try {
			userService.transaction();
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
		
		users = userService.getUserListBySql();
		System.out.println(users);
		
		userService.updateUserBySql("cjy", "sql cjy");
	}
	

	@Autowired
	UserRepositoryServiceImpl userService;
}

