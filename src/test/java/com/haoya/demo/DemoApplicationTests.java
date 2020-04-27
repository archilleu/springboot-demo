package com.haoya.demo;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class DemoApplicationTests {

	public static void main(String[] argc0){

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String pwd = bCryptPasswordEncoder.encode("123456");
		System.out.println(pwd);
	}
	//@Test
	public void contextLoads() {
		String pwd = bCryptPasswordEncoder.encode("123456");
		System.out.println(pwd);
	}

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

}
