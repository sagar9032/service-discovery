package com.discover.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDateTime;

@FieldDefaults(level=AccessLevel.PRIVATE) @Getter @Setter @AllArgsConstructor
public class ApiError {
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="dd-MM-yyyy hh:mm:ss") LocalDateTime timestamp;
	HttpStatus status;
	String message;
	List<String> errors;
	public ApiError(LocalDateTime timestamp, HttpStatus status, String message, String error) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.message = message;
		errors = Arrays.asList(error);
	}
}