package com.hoya.app.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoya.app.demo.controller.DemoController;
import com.hoya.app.demo.entity.User;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@WebMvcTest(DemoController.class)
public class DemoControllerTest {
	
	@Test
	public void testIndex() throws Exception {
		String uri = "/demo/index";
		mvc.perform(get(uri))
		.andExpect(view().name("/demo/demo"));
	}

	@Test
	public void testComsumesJson() throws Exception {
		String uri = "/demo/consumes/sample.json";
		User user = new User("name", "pwd", "ignore");
		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(user);
		mvc.perform(post(uri).content(str)
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.name").value(user.getName()))
		.andExpect(jsonPath("$.password").value(user.getPassword()))
		.andExpect(content().string(str));
	}
	
	@Test
	public void testRedirect() throws Exception {
		String uri = "/demo/redirect-demo";
		String uriRedirect = "/demo/redirect";
		mvc.perform(get(uri))
		.andExpect(redirectedUrl(uriRedirect));
	}

	@Test
	public void testForward() throws Exception {
		String uri = "/demo/forward-demo";
		String uriForward = "/demo/forward";
		mvc.perform(get(uri))
		.andExpect(forwardedUrl(uriForward));
	}
	
	@Test
	public void testMvc() throws Exception {
		int userId = 1;
		mvc.perform(get("/demo/var/{id}/get.json", userId))
		.andExpect(content().string("success" + userId));
	}
	
	@Test
	public void testFileUpload() throws Exception {
		mvc.perform(multipart("/demo/form").file("file", DemoController.FILE_UPLOAD_CONTENT))
		.andExpect(content().string("success"));
	}

	@Autowired
	private MockMvc mvc;
}
