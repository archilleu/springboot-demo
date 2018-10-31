package com.hoya.app.demo.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoya.app.demo.entity.User;
import com.hoya.app.demo.rest.RestfulController;

@RunWith(SpringRunner.class)
@WebMvcTest(RestfulController.class)
@ActiveProfiles("dev")
public class RestfulControllerTest {

	@Test
	public void testOrder() throws Exception {
		
		User user = new User("name", "password", "ignore");
		ObjectMapper mapper = new ObjectMapper();
		String uri_add = "/api/v1/user";
		String str = mapper.writeValueAsString(user);

		mvc.perform(post(uri_add).content(str)
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.code").value(200))
		.andExpect(jsonPath("$.msg").value("success"))
		.andExpect(jsonPath("$.error").value("200 OK"));

		user.setName("throw");
		str = mapper.writeValueAsString(user);
		mvc.perform(post(uri_add).content(str)
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isForbidden())
		.andExpect(jsonPath("$.code").value(403));
		
		String uri_get = "/api/v1/user/u1";
		mvc.perform(get(uri_get))
		.andExpect(status().isOk());
	}
	
	public void testRestClient() throws Exception {
		
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
		RestTemplate client = restTemplateBuilder.build();
		String uri = "http://localhost/api/v1/user/u2";
		User user = client.getForObject(uri, User.class);
		assertEquals("u1", user.getName());
		assertEquals("password", user.getPassword());
		assertEquals("ignore", user.getIgnore());
	}
	
	@Autowired
	private MockMvc mvc;
}