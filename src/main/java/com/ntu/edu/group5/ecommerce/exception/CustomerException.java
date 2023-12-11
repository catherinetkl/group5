package com.ntu.edu.group5.ecommerce.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class CustomerException extends RuntimeException {
    public CustomerException() {
        super();
    }

    public CustomerException(String message) {
        super(message);
    }
}
