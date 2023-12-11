package com.ntu.edu.group5.ecommerce.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class OrderException extends RuntimeException {
    public OrderException(){
        super();
    }

    public OrderException(String message) {
        super(message);
    }
}
