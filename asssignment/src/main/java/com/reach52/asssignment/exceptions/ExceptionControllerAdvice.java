package com.reach52.asssignment.exceptions;

import com.reach52.asssignment.dto.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


//Exception handler for the REST endpoints
@RestControllerAdvice
public class ExceptionControllerAdvice {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception ex) {
        logger.error(ex.getMessage());
        return ex.getMessage();
    }

    //To handle the max upload size exceeded exception
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ErrorMessage> exceptionHandler1(MultipartException ex, RedirectAttributes redirectAttributes) {
        ErrorMessage error = new ErrorMessage();
        error.setErrorCode(HttpStatus.NOT_ACCEPTABLE.value());
        error.setMessage(ex.getCause().getMessage());
        logger.error(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.OK);
    }

    //Handler for handling exception of type NoSuchCustomerException
    @ExceptionHandler(NoSuchCustomerException.class)
    public ResponseEntity<ErrorMessage> exceptionHandler2(NoSuchCustomerException ex) {
        ErrorMessage error = new ErrorMessage();
        error.setErrorCode(HttpStatus.BAD_GATEWAY.value());
        error.setMessage(ex.getMessage());
        logger.error(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.OK);

    }

    //Handler for handling exception of type NoSuchCustomerException
    @ExceptionHandler(InvalidParametersException.class)
    public ResponseEntity<ErrorMessage> exceptionHandler3(InvalidParametersException ex) {
        ErrorMessage error = new ErrorMessage();
        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());
        logger.error(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.OK);
    }

} 