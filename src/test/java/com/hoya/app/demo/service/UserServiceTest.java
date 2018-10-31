package com.hoya.app.demo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.BDDMockito.given;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hoya.app.demo.dao.UserDao;
import com.hoya.app.demo.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class UserServiceTest {
	
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
		System.out.println("unit test method before...");
	}
	
	@After
	public void afterTest() {
		System.out.println("unit test method after...");
	}
	
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
		
		user.setName("new user");
		userService.delete(user);
		assertNull(userService.getUser("new user"));
		
		try {
			userService.transaction();
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
		
	}
	
	@Test
	public void testMock() {
		Long expected = 100L;
		//模拟userDao的rowCount函数返回expected
		given(this.userDao.rowCount()).willReturn(expected);
		Long result = userService.mockTest();
		assertEquals(expected, result);
	}

	@Autowired
	UserServiceImpl userService;
	
	@MockBean
	private UserDao userDao;
}
