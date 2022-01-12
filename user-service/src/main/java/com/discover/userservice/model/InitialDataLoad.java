package com.discover.userservice.model;

import com.discover.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import java.util.Arrays;
import java.util.List;

@Component @Slf4j
public class InitialDataLoad {

	@Autowired private UserRepository userRepository;

	@EventListener(ApplicationReadyEvent.class)
    public void eventListenerExecute(){
		byte age = 21;
		User user1 = new User(1L, "Aayush Saxena", "M", age);
		User user2 = new User(2L, "Ashwani Singh", "F", age);
		User user3 = new User(3L, "Purnachandra Rao", "M", age);
		User user4 = new User(4L, "Arun Kumar Regulagadda", "F", age);
		List<User> listOfUsers = Arrays.asList(user1,user2,user3,user4);
		userRepository.saveAll(listOfUsers);
        log.info("Initial data has been loaded.");
    }

}