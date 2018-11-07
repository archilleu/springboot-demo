package com.hoya.app.demo.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hoya.app.demo.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class UserRepositoryServiceTest {
	
	@BeforeClass
	public static void beforeClassTest() {
		System.out.println("unit test class before...");
	}
	
	@AfterClass
	public static void afterClassTest() {
		System.out.println("unit test class after...");
	}
	
	@Before
	public void beforeTest() {
		userService.clear();
		System.out.println("unit test method before...");
	}
	
	@After
	public void afterTest() {
		System.out.println("unit test method after...");
	}
	
	@Test
	public void testRowCount() {
		User user = new User("name", "password", null);
		userService.saveUser(user);
		Long expected = 1L;
		Long count = userService.rowCount();
		assertEquals(expected, count);
		
		String name = "name";
		count = userService.rowCount(name);
		assertEquals(expected, count);
		
		count = userService.rowCount("not exists");
		assertEquals(new Long(0L), count);
	}
	
	@Test
	public void testGetSave() {
		User user = new User("name", "password", null);
		userService.saveUser(user);
		User expected = userService.getUser(user.getName());
		assertEquals(expected, user);
		
		user = userService.getUser("none");
		assertNull(user);
	}
	
	@Test
	public void testUpdate() {
		User user = new User("name", "password", null);
		userService.saveUser(user);
		user.setPassword("new password");
		userService.updatePassword(user);
		User expected = userService.getUser(user.getName());
		assertEquals(expected.getPassword(), user.getPassword());
		assertEquals(expected, user);
	}
	
	@Test
	public void testList() {
		User user1 = new User("name1", "password", null);
		User user2 = new User("name2", "password", null);
		userService.saveUser(user1);
		userService.saveUser(user2);

		List<User> users = userService.getUserList();
		long expectedCount = 2L;
		assertEquals(expectedCount, users.size());
		
		User user = users.get(0);
		assertTrue(user.equals(user1) || user.equals(user2));
		user = users.get(1);
		assertTrue(user.equals(user1) || user.equals(user2));
	}
	
	@Test
	public void testDelete() {
		User user = new User("name", "password", null);
		userService.saveUser(user);
		assertNotNull(userService.getUser(user.getName()));
		userService.delete(user);
		assertNull(userService.getUser(user.getName()));
	}
	
	@Test
	public void testTransaction() {
		
		try {
			userService.transaction();
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testSQL() {
		User user1 = new User("name1", "password", null);
		User user2 = new User("name2", "password", null);
		userService.saveUser(user1);
		userService.saveUser(user2);

		int page = 0;
		int size = 2;
		List<User> users = userService.getUserListSortAndPage(page, size);
		long expectedCount = 2L;
		assertEquals(expectedCount, users.size());
		
		User user = users.get(0);
		assertTrue(user.equals(user1));
		user = users.get(1);
		assertTrue(user.equals(user2));
		
		users = userService.getUserListBySql();
		assertEquals(expectedCount, users.size());
		
		String name = "name1";
		String password = "sql password";
		userService.updateUserBySql(name, password);
		user = userService.getUser(name);
		assertEquals(password, user.getPassword());
	}

	@Autowired
	UserRepositoryServiceImpl userService;
}

