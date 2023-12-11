package com.ntu.edu.group5.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CartItemNotFoundException extends RuntimeException{

    public CartItemNotFoundException() {
        super();
    }

    public CartItemNotFoundException(String message) {
        super(message);
    }
}
