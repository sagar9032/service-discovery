package com.discover.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.discover.userservice.dto.DataBaseDTO;
import com.discover.userservice.utils.ProjectUtils;
import lombok.extern.slf4j.Slf4j;
import java.util.Collections;
import java.util.List;

@RestController @Slf4j(topic="DBHealthCheckController")
public class HealthCheckController {
	
	@Autowired RestTemplate restTemplate;
	@Value("${spring.application.name}") String appName;
	@Autowired DiscoveryClient discoveryClient;
	
	@GetMapping("/db/health")
    public ResponseEntity<DataBaseDTO> health() throws JsonProcessingException {
    	log.trace("RestController: DBHealthCheckController, Mapping: /health-GET, Method: health");
		log.info("Database status is being retrieved..");
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(headers);
		String jsonStr = restTemplate.exchange(ProjectUtils.DB_HEALTH_URL, HttpMethod.GET, entity, String.class).getBody();
		ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        DataBaseDTO result = mapper.readValue(jsonStr, DataBaseDTO.class);
        log.debug("Status is {}, Database is {} and validationQuery is {}",result.getStatus(), result.getDetails().get(0).getDb(), result.getDetails().get(0).getValidationQuery());
		log.info("Status is {}, Database is {} and validationQuery is {}",result.getStatus(), result.getDetails().get(0).getDb(), result.getDetails().get(0).getValidationQuery());
		return new ResponseEntity<>(result,HttpStatus.OK);
    }

	@GetMapping("/service-instances")
	public List<ServiceInstance> serviceInstancesByApplicationName() {
		List<ServiceInstance> serviceInstanceList = this.discoveryClient.getInstances("FIRST-USER-SERVICE");
		log.info(serviceInstanceList.toString()+": "+appName);
		return serviceInstanceList;
	}

}