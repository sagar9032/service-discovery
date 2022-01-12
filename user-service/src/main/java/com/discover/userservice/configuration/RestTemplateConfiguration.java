package com.discover.userservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;

@Configuration
public class RestTemplateConfiguration {

	@LoadBalanced @Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Primary @Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}