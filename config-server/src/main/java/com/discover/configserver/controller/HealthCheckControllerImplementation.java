package com.discover.configserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.netflix.discovery.EurekaClient;

@Slf4j
@RestController @RequestMapping
public class HealthCheckControllerImplementation {

	@Value("${spring.application.name}")
	private String appName;

	@Autowired
	private Environment env;

	@Autowired @Lazy
	private EurekaClient eurekaClient;

	@GetMapping("/health")
	public String healthCheck() {
		log.info("{} can able to login to the {}.", env.getProperty("spring.security.user.name"), appName);
		return "Hello from 'EXTERNAL-CONFIG-SERVER'!";
	}

}