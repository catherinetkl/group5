package com.ntu.edu.group5.ecommerce.exception;

public class SellerNotFoundException extends RuntimeException {

    public SellerNotFoundException() {
        super();
    }

    public SellerNotFoundException(String message) {
        super(message);
    }
}
