package com.hoya.app.demo.service;

import static org.junit.Assert.*;

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
public class CacheTest {

	@Test
	public void testCache() {
		Long actual = service.getRow();
		assertEquals(actual, CacheService.ROW_COUNT);
		
		Long expected = 2L;
		actual = service.getRow(expected);
		assertEquals(expected, actual);
		
		User user = new User("name", "password", "ignore");
		User result = service.put(user);
		assertEquals(user, result);
		
		service.evict(user);

		actual = service.getRow();
		assertEquals(actual, CacheService.ROW_COUNT);
		
		expected = 2L;
		actual = service.getRow(expected);
		
		assertEquals(2, service.getCall());
	}

	@Autowired
	CacheService service;
}
