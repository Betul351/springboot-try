package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.repository.LogRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class TryApplicationTests {


	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LogRepository logRepository;

	@Transactional
	@Rollback(value = false)
	@Test
	public void testCreateUser(){
		UserRequest userRequest = new UserRequest("Nehir","nehir@gmail.com","nehir123");

		try {
			userService.createUser(userRequest);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		assertEquals(0,userRepository.findAll().size());
		assertEquals(1,logRepository.findAll().size());
	}


}
