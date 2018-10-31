package com.hoya.app.demo.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoya.app.demo.entity.User;
import com.hoya.app.exception.AlreadyExistException;
import com.hoya.app.exception.Success;

@RestController
@RequestMapping("/api/v1")
public class RestfulController {

	@GetMapping("/user/{userId}")
	public User getUser(@PathVariable String userId) throws Exception {
		return new User(userId, "password", "ignore");
	}

	@PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Success addUser(@RequestBody User user) throws Exception {
		if (user.getName().equals("throw"))
			throw new AlreadyExistException();

		return Success.kSuccess;
	}
}