package com.ntu.edu.group5.ecommerce.exception;

public class SellerNotFoundException extends RuntimeException {
    SellerNotFoundException(Long id) {
        super("Could not find seller with id : " + id + ".");
    }
    
}
