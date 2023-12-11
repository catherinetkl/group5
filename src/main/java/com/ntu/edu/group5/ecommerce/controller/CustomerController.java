package com.ntu.edu.group5.ecommerce.controller;

import java.util.List;

import jakarta.validation.Valid;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ntu.edu.group5.ecommerce.entity.Address;
import com.ntu.edu.group5.ecommerce.entity.Customer;
import com.ntu.edu.group5.ecommerce.entity.CustomerDTO;
import com.ntu.edu.group5.ecommerce.entity.CustomerUpdateDTO;
import com.ntu.edu.group5.ecommerce.entity.SessionDTO;
import com.ntu.edu.group5.ecommerce.exception.CustomerException;
import com.ntu.edu.group5.ecommerce.exception.CustomerNotFoundException;
import com.ntu.edu.group5.ecommerce.exception.LoginException;
import com.ntu.edu.group5.ecommerce.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService customerService;

    // @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /* SEARCH - Retrieves a list of customers based on first name and last name parameters.
     * Returns a ResponseEntity with the list of customers if found, otherwise,
     * returns a NOT_FOUND status.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Customer>> searchCustomers(@RequestParam String firstName,
            @RequestParam String lastName) {
        try {
            List<Customer> foundCustomers = customerService.searchCustomers(firstName, lastName);
            return new ResponseEntity<>(foundCustomers, HttpStatus.OK);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /* CREATE - Creates a new customer using the provided customer data.
     * Returns a ResponseEntity with the created customer if successful, otherwise,
     * returns a BAD_REQUEST status.
     */
    @PostMapping("")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        try {
            Customer newCustomer = customerService.createCustomer(customer);
            return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /* READ (GET ALL) - Retrieves a list of all customers.
     * Returns a ResponseEntity with the list of customers if found, otherwise,
     * returns a NOT_FOUND status.
     */
    @GetMapping("")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        try {
            List<Customer> allCustomers = customerService.getAllCustomers();
            return new ResponseEntity<>(allCustomers, HttpStatus.OK);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /* READ (GET ONE) - Retrieves a customer based on the provided id.
     * Returns a ResponseEntity with the customer if found, otherwise, returns a
     * NOT_FOUND status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        try {
            Customer foundCustomer = customerService.getCustomer(id);
            return new ResponseEntity<>(foundCustomer, HttpStatus.OK);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /* UPDATE - Updates a customer based on the provided id and customer data.
     * Returns a ResponseEntity with the updated customer if successful, otherwise,
     * returns a NOT_FOUND status.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id,
            @RequestBody CustomerUpdateDTO customerUpdateDTO) {
        try {
            Customer updatedCustomer = customerService.updateCustomer(customerUpdateDTO, id);
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /* UPDATE (Contact No or Email) - Updates a customer's contact number or email
     * based on the provided id and customer data.
     * Returns a ResponseEntity with the updated customer if successful, otherwise,
     * returns a NOT_FOUND status.
     */
    @PutMapping("/{id}/contact-email")
    public ResponseEntity<Customer> updateCustomerContactNoOrEmail(@PathVariable Long id,
            @RequestBody CustomerUpdateDTO customerUpdateDTO) {
        try {
            Customer updatedCustomer = customerService.updateCustomerContactNoOrEmail(customerUpdateDTO, id);
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /* UPDATE (Password) - Updates a customer's password based on the provided id
     * and customer data.
     * Returns a ResponseEntity with the updated customer if successful, otherwise,
     * returns a NOT_FOUND status.
     */
    @PutMapping("/{id}/password")
    public ResponseEntity<SessionDTO> updateCustomerPassword(@PathVariable Long id,
            @RequestBody CustomerDTO customerDTO,
            @RequestParam String currentPassword) {
        try {
            SessionDTO sessionDTO = customerService.updateCustomerPassword(id, customerDTO, currentPassword);
            return new ResponseEntity<>(sessionDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /* UPDATE (Address) - Updates a customer's address based on the provided id,
     * address data, and address type.
     * Returns a ResponseEntity with the updated customer if successful, otherwise,
     * returns a NOT_FOUND status.
     */
    @PutMapping("/{id}/address/{type}")
    public ResponseEntity<Customer> updateAddress(@PathVariable Long id, @PathVariable String type,
            @RequestBody CustomerUpdateDTO customerUpdateDTO, Address address) {
        try {
            Customer updatedCustomer = customerService.updateAddress(customerUpdateDTO, id, address, type);
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        } catch (CustomerException | CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /* DELETE - Deletes a customer based on the provided id.
     * Returns a ResponseEntity with a NO_CONTENT status if successful, otherwise,
     * returns a NOT_FOUND status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteCustomer(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /* DELETE (By Id) - Deletes a customer based on the provided id and token.
     * Returns a ResponseEntity with a NO_CONTENT status if successful, otherwise,
     * returns a NOT_FOUND status.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<SessionDTO> deleteCustomerByContactNoOrEmail(@RequestParam(required = false) String contactNo,
            @RequestParam(required = false) String email, @RequestParam String token) {
        try {
            SessionDTO sessionDTO = customerService.deleteCustomerByContactNoOrEmail(contactNo, email, token);
            return new ResponseEntity<>(sessionDTO, HttpStatus.OK);
        } catch (CustomerNotFoundException | LoginException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
