package com.discover.servicediscovery;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.discover.servicediscovery.controller.EurekaClientsController;

@SpringBootTest
class ServiceDiscoveryApplicationTests {
	
	@Autowired
	private EurekaClientsController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}