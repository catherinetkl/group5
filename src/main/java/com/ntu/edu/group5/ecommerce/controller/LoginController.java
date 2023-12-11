package com.ntu.edu.group5.ecommerce.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ntu.edu.group5.ecommerce.entity.Customer;
import com.ntu.edu.group5.ecommerce.entity.CustomerDTO;
import com.ntu.edu.group5.ecommerce.entity.Seller;
import com.ntu.edu.group5.ecommerce.entity.SellerDTO;
import com.ntu.edu.group5.ecommerce.entity.SessionDTO;
import com.ntu.edu.group5.ecommerce.entity.UserSession;
import com.ntu.edu.group5.ecommerce.service.CustomerService;
import com.ntu.edu.group5.ecommerce.service.LoginLogoutService;
import com.ntu.edu.group5.ecommerce.service.SellerService;

@RestController
public class LoginController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LoginLogoutService loginService;

    @Autowired
    private SellerService sellerService;

    // Handler to register a new customer
    @PostMapping(value = "/register/customer", consumes = "application/json")
    public ResponseEntity<Customer> registerAccountHandler(@Valid @RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.CREATED);
    }

    // Handler to login a customer
    @PostMapping(value = "/login/customer", consumes = "application/json")
    public ResponseEntity<UserSession> loginCustomerHandler(@Valid @RequestBody CustomerDTO customerdto) {
        return new ResponseEntity<>(loginService.loginCustomer(customerdto), HttpStatus.ACCEPTED);
    }

    // Handler to logout a customer
    @PostMapping(value = "/logout/customer", consumes = "application/json")
    public ResponseEntity<SessionDTO> logoutCustomerHandler(@RequestBody SessionDTO sessionToken) {
        return new ResponseEntity<>(loginService.logoutCustomer(sessionToken), HttpStatus.ACCEPTED);
    }

    /*********** SELLER REGISTER LOGIN LOGOUT HANDLER ************/
    @PostMapping(value = "/register/seller", consumes = "application/json")
    public ResponseEntity<Seller> registerSellerAccountHandler(@Valid @RequestBody Seller seller) {
        return new ResponseEntity<>(sellerService.createSeller(seller), HttpStatus.CREATED);
    }

    // Handler to login a seller
    @PostMapping(value = "/login/seller", consumes = "application/json")
    public ResponseEntity<UserSession> loginSellerHandler(@Valid @RequestBody SellerDTO seller) {
        return new ResponseEntity<>(loginService.loginSeller(seller), HttpStatus.ACCEPTED);
    }

    // Handler to logout a seller
    @PostMapping(value = "/logout/seller", consumes = "application/json")
    public ResponseEntity<SessionDTO> logoutSellerHandler(@RequestBody SessionDTO sessionToken) {
        return new ResponseEntity<>(loginService.logoutSeller(sessionToken), HttpStatus.ACCEPTED);
    }
}
