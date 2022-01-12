package com.discover.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TicketNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public TicketNotFoundException(Long id) {
		super("Count not found the ticket id: "+id);
	}
	public TicketNotFoundException(String name) {
		super("Count not found the ticket name: "+name);
	}
}