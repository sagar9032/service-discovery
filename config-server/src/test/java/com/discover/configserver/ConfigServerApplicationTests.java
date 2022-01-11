package com.discover.configserver;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.discover.configserver.controller.HealthCheckControllerImplementation;

@SpringBootTest
class ConfigServerApplicationTests {
	
	@Autowired
	private HealthCheckControllerImplementation controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}