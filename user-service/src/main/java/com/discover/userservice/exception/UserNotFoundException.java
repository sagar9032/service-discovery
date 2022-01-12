package com.discover.userservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public UserNotFoundException(Long id) {
		super("Count not found the user id: "+id);
	}
	public UserNotFoundException(String name) {
		super("Count not found the user name: "+name);
	}
}