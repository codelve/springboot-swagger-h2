package com.reach52.asssignment.exceptions;

import com.reach52.asssignment.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

 
//Exception handler for the REST endpoints
@RestControllerAdvice
public class ExceptionControllerAdvice {
 
	@ExceptionHandler(Exception.class)
	public String  exceptionHandler(Exception ex) {
		return  ex.getMessage();
	}
	
	//Handler for handling exception of type NoSuchCustomerException
	@ExceptionHandler(NoSuchCustomerException.class)
	public ResponseEntity<ErrorMessage> exceptionHandler2(NoSuchCustomerException ex) {
		 ErrorMessage error = new ErrorMessage();
	        error.setErrorCode(HttpStatus.BAD_GATEWAY.value());
	        error.setMessage(ex.getMessage());
	        return new ResponseEntity<>(error, HttpStatus.OK);
		 
	}

	//Handler for handling exception of type NoSuchCustomerException
	@ExceptionHandler(InvalidParametersException.class)
	public ResponseEntity<ErrorMessage> exceptionHandler3(InvalidParametersException ex) {
		ErrorMessage error = new ErrorMessage();
		error.setErrorCode(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.OK);
	}

} 