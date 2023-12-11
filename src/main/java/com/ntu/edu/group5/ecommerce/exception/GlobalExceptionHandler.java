package com.ntu.edu.group5.ecommerce.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // // this is handler for CustomerNotFoundException
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(CustomerNotFoundException cnfe, WebRequest wr) {
        ErrorResponse err = new ErrorResponse(LocalDateTime.now(), cnfe.getMessage(), wr.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    // this is handler for CustomerException
    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<ErrorResponse> handleCustomerException(CustomerException ce, WebRequest wr) {
        ErrorResponse err = new ErrorResponse(LocalDateTime.now(), ce.getMessage(), wr.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
    }

    // this is handler for NoHandlerFoundException
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException nhfe, WebRequest wr) {
        ErrorResponse err = new ErrorResponse(LocalDateTime.now(), nhfe.getMessage(), wr.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    // this is handler for MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException manve, WebRequest wr) {
        List<ObjectError> errors = manve.getBindingResult().getAllErrors();
        String message = "";
        for (ObjectError error : errors) {
            message += error.getDefaultMessage() + "\n";
        }
        ErrorResponse err = new ErrorResponse(LocalDateTime.now(), message, wr.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    // this is handler for Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest wr) {
        // We can log the exception here
        // logger.error(ex.getMessage(), ex);

        // return generic error message;
        ErrorResponse err = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), wr.getDescription(false) );
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    // this is handler for EmptyResultDataAccessException
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        ErrorResponse err = new ErrorResponse(LocalDateTime.now(), "Item does not exist.", ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }
}
