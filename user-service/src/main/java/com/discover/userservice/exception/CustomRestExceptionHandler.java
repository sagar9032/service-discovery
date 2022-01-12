package com.discover.userservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@ControllerAdvice @RestController
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError apiError= new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST,"Validation Errors",ex.getBindingResult().getFieldErrors().stream().map(error -> error.getObjectName()+" : "+error.getDefaultMessage()).toList());
		return new ResponseEntity<>(apiError,apiError.getStatus());
	}
	
	@Override protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError apiError= new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST,"Missing Parameters",ex.getParameterName()+" parameter is missing");
		return new ResponseEntity<>(apiError,apiError.getStatus());
	}
	
	@Override protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError apiError = new ApiError(LocalDateTime.now(),HttpStatus.NOT_FOUND, ex.getLocalizedMessage(),String.format("Could not find the %s meethod for URL %s",ex.getHttpMethod(),ex.getRequestURL()));
		return new ResponseEntity<>(apiError,apiError.getStatus());
	}
	
	@Override protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
		ApiError apiError = new ApiError(LocalDateTime.now(),HttpStatus.METHOD_NOT_ALLOWED,ex.getLocalizedMessage(),builder.toString());
		return new ResponseEntity<>(apiError,apiError.getStatus());
	}
	
	@Override protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t+", "));
		ApiError apiError = new ApiError(LocalDateTime.now(),HttpStatus.UNSUPPORTED_MEDIA_TYPE,ex.getLocalizedMessage(),builder.substring(0,builder.length()-2));
		return new ResponseEntity<>(apiError,apiError.getStatus());
	}
	
	@Override protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError apiError = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST,"Malformed JSON request",ex.getMessage());
		return new ResponseEntity<>(apiError,apiError.getStatus());
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
		List<String> errors = new ArrayList<>();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " +violation.getPropertyPath() + ": " + violation.getMessage());
		}
		ApiError apiError = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST,ex.getLocalizedMessage(),errors);
		return new ResponseEntity<>(apiError,apiError.getStatus());
	}
	
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	protected ResponseEntity<ApiError> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
		ApiError apiError = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST,"Method Argument Type Mismatch",ex.getName()+" should be of type "+ex.getRequiredType().getName());
		return new ResponseEntity<>(apiError,apiError.getStatus());
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<ApiError> handleResourceNotFoundException(UserNotFoundException ex) {
		ApiError apiError = new ApiError(LocalDateTime.now(),HttpStatus.NOT_FOUND,"User Not Found",ex.getMessage());
		return new ResponseEntity<>(apiError,apiError.getStatus());
	}
	
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<ApiError> handleAll(Exception ex) {
		ApiError apiError = new ApiError(LocalDateTime.now(),HttpStatus.INTERNAL_SERVER_ERROR,ex.getLocalizedMessage(),"Error occurred");
		return new ResponseEntity<>(apiError,apiError.getStatus());
	}

}