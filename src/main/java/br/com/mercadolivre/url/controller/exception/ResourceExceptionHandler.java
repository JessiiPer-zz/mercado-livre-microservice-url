package br.com.mercadolivre.url.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.mercadolivre.url.service.exception.InvalidUrlException;
import br.com.mercadolivre.url.service.exception.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler{
	
	@ExceptionHandler(InvalidUrlException.class)
	public ResponseEntity<StandardError> invalidUrl(InvalidUrlException e, HttpServletRequest request){
		String error = "Invalid Url";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(status.value(),error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err); 
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
		String error = "Resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(status.value(),error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
}
