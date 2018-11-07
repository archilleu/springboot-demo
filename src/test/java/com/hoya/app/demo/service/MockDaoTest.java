package com.hoya.app.demo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

import com.hoya.app.demo.dao.UserDao;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class MockDaoTest {

	@Test
	public void testMock() {
		Long expected = 100L;
		//模拟userDao的rowCount函数返回expected
		given(this.userDao.rowCount()).willReturn(expected);
		Long result = service.mockTest();
		assertEquals(expected, result);
	}

	@Autowired
	MockService service;

	@MockBean
	private UserDao userDao;
}
