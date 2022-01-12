package com.discover.userservice.service;

import com.discover.userservice.exception.UserNotFoundException;
import com.discover.userservice.model.User;
import com.discover.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Service @Slf4j
public class UserServiceImplementation implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> retrieveAllUsers() {
		List<User> users = userRepository.findAll();
		log.debug("Available users from user-repository: {}",users);
		log.info("Available users from user-repository: {}",users);
		return users;
	}

	@Override
	public User createUser(User newUser) {
		User createUser = userRepository.save(newUser);
		log.debug("Saved User details: {}",createUser);
		log.info("Saved User details: {}",createUser);
		return createUser;
    }

	@Override
	public User updateUser(Long id, User editUser) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		user.setId(id);
		user.setName(editUser.getName());user.setGender(editUser.getGender());user.setAge(editUser.getAge());
		User updateUser = userRepository.save(user);
		log.debug("Updated User details: {}",updateUser);
		log.info("Updated User details: {}",updateUser);
		return updateUser;
	}
	
	@Override
    public User retrieveUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User is not found for the id: "+id));
		log.debug("Retrieved user details from the user-repository: {}",user);
		log.info("Retrieved user details from the user-repository: {}",user);
        return user;
    }

	@Override
	public void deleteById(Long id) {
		log.debug("Retrieved user id for the delete: {}",id);
		log.info("Retrieved user id for the delete: {}",id);
		userRepository.deleteById(id);
	}

}