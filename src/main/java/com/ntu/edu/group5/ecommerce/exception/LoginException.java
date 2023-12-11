package com.ntu.edu.group5.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super(message);
    }
}
