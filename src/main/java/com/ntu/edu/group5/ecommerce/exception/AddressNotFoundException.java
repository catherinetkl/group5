package com.ntu.edu.group5.ecommerce.exception;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(Integer id) {
        super("Could not find address with id: " + id + ".");
    }
}
