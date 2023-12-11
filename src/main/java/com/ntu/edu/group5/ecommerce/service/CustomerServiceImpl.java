package com.ntu.edu.group5.ecommerce.service;

import java.util.List;
import java.util.ArrayList;

import java.util.Optional;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ntu.edu.group5.ecommerce.entity.Address;
import com.ntu.edu.group5.ecommerce.entity.Cart;
import com.ntu.edu.group5.ecommerce.entity.Customer;
import com.ntu.edu.group5.ecommerce.entity.CustomerDTO;
import com.ntu.edu.group5.ecommerce.entity.CustomerUpdateDTO;
import com.ntu.edu.group5.ecommerce.entity.Order;
import com.ntu.edu.group5.ecommerce.entity.SessionDTO;
import com.ntu.edu.group5.ecommerce.entity.UserSession;
import com.ntu.edu.group5.ecommerce.exception.CustomerException;
import com.ntu.edu.group5.ecommerce.exception.CustomerNotFoundException;
import com.ntu.edu.group5.ecommerce.exception.LoginException;
import com.ntu.edu.group5.ecommerce.repository.CustomerDao;
import com.ntu.edu.group5.ecommerce.repository.SessionDao;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private CustomerDao customerDao;

    private LoginLogoutService loginService;

    private SessionDao sessionDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CustomerServiceImpl(CustomerDao customerDao,
            LoginLogoutService loginService,
            SessionDao sessionDao) {
        this.customerDao = customerDao;
        this.loginService = loginService;
        this.sessionDao = sessionDao;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        // Set creation timestamp for the customer
        customer.setCreatedOn(LocalDateTime.now());

        // Create new cart for the customer
        Cart cart = new Cart();

        // Log the cart details
        LOGGER.info("Cart details: {}", cart);

        // Set the cart for the customer
        customer.setCart(cart);

        // Initialize the customer's order list
        customer.setOrders(new ArrayList<>());

        return customer;
    }

    // Method to get all customers
    @Override
    public List<Customer> getAllCustomers() throws CustomerNotFoundException {
        // Retrieve all customers from the database
        List<Customer> customers = customerDao.findAll();

        // Check if the list is empty
        if (customers.isEmpty()) {
            throw new CustomerNotFoundException("No record exists");
        }

        // Return the list of customers
        return customers;
    }

    // Method to get customer by ID
    @Override
    public Customer getCustomer(Long customerId) throws CustomerNotFoundException {

        // Check if the customer exists
        Optional<Customer> opt = customerDao.findById(customerId);

        if (opt.isEmpty()) {
            throw new CustomerNotFoundException("Customer does not exist");
        }

        // Retrieve the customer from the database
        Customer existingCustomer = opt.get();

        // Return the existing customer
        return existingCustomer;
    }

    // Method to get logged in customer details
    @Override
    public Customer getLoggedInCustomerDetails(String token) {
        // Check if the token contains "customer"
        if (!token.contains("customer")) {
            throw new LoginException("Invalid session token for customer");
        }

        // Check the status of the token using the login service
        loginService.checkTokenStatus(token);

        // Retrieve the user session based on the token
        UserSession user = sessionDao.findByToken(token)
                .orElseThrow(() -> new LoginException("User session not found"));

        // Find the customer in the database by user ID obtained from the session
        Optional<Customer> opt = customerDao.findById(user.getUserId().longValue());

        // Check if the customer exists
        if (opt.isEmpty()) {
            throw new CustomerNotFoundException("Customer does not exist");
        }

        // Get the existing customer from the optional
        Customer existingCustomer = opt.get();

        // Return the existing customer
        return existingCustomer;
    }

    // Method to get all customers - only seller or admin can get all customers
    // - check validity of seller token
    @Override
    public List<Customer> getAllCustomers(String token)
            throws CustomerNotFoundException {

        // update to seller token
        if (!token.contains("seller")) {
            throw new LoginException("Invalid session token.");
        }

        // check token status
        loginService.checkTokenStatus(token);

        // retrieve user session based on token
        List<Customer> customers = customerDao.findAll();

        // check if customers exist
        if (customers.isEmpty()) {
            throw new CustomerNotFoundException("No record exists");
        }

        // return list of customers
        return customers;
    }

    // Method to get customer by contact number or email
    @Override
    public Customer getCustomerByContactNoOrEmail(String contactNo, String email)
            throws CustomerNotFoundException {

        // Check if the customer exists
        Optional<Customer> opt = customerDao.findByContactNoOrEmail(contactNo, email);

        if (opt.isEmpty()) {
            throw new CustomerNotFoundException("Customer does not exist");
        }

        // Retrieve the customer from the database
        Customer existingCustomer = opt.get();

        // Return the existing customer
        return existingCustomer;
    }

    // Method to get customer by first name or last name
    @Override
    public List<Customer> searchCustomers(String firstName, String lastName)
            throws CustomerNotFoundException {

        // check if first name or last name is empty
        if (firstName.isEmpty() || lastName.isEmpty()) {
            throw new CustomerNotFoundException("No record exists");
        }

        // find customer by first name or last name
        List<Customer> foundCustomers = customerDao.findByFirstNameOrLastName(firstName, lastName);
        return foundCustomers;
    }

    // Method to get customer orders by customer ID
    @Override
    public List<Order> getCustomerOrders(Long customerId) throws CustomerException {
        // Find the customer in the database by customer ID
        Optional<Customer> opt = customerDao.findById(customerId);

        // Check if the customer exists
        if (opt.isEmpty()) {
            throw new CustomerNotFoundException("Customer does not exist");
        }

        // Get the existing customer from the database
        Customer existingCustomer = opt.get();

        // Get the list of orders for the customer
        List<Order> orders = existingCustomer.getOrders();

        if (orders.isEmpty()) {
            throw new CustomerException("No orders found for the customer");
        }

        // Return the list of orders
        return existingCustomer.getOrders();
    }

    // Method to update customer details
    @Override
    public Customer updateCustomer(CustomerUpdateDTO customer, Long customerId)
            throws CustomerNotFoundException {
        // Check if the customer exists
        Optional<Customer> opt = customerDao.findById(customerId);

        if (opt.isEmpty()) {
            throw new CustomerNotFoundException("Customer does not exist");
        }

        // Retrieve the customer from the database
        Customer existingCustomer = opt.get();

        // Update the customer details
        if (customer.getFirstName() != null) {
            existingCustomer.setFirstName(customer.getFirstName());
        }

        if (customer.getLastName() != null) {
            existingCustomer.setLastName(customer.getLastName());
        }

        if (customer.getEmail() != null) {
            existingCustomer.setEmail(customer.getEmail());
        }

        if (customer.getContactNo() != null) {
            existingCustomer.setContactNo(customer.getContactNo());
        }

        if (customer.getPassword() != null) {
            existingCustomer.setHashedPassword(customer.getPassword());
        }

        // Update or add the new address
        if (customer.getAddress() != null && !customer.getAddress().isEmpty()) {
            for (Map.Entry<String, Address> entry : customer.getAddress().entrySet()) {
                String type = entry.getKey();
                Address address = entry.getValue();

                // Assuming you have a method to find the specific address in the list
                Address existingAddress = findAddressByType(existingCustomer.getAddress(), type);

                // Update or add the new address
                if (existingAddress != null) {
                    // Update the existing address
                    existingAddress.setBlockNumber(address.getBlockNumber());
                    existingAddress.setStreetName(address.getStreetName());
                    existingAddress.setBuildingName(address.getBuildingName());
                    existingAddress.setCity(address.getCity());
                    existingAddress.setState(address.getState());
                    existingAddress.setPostalCode(address.getPostalCode());
                } else {
                    // Add the new address
                    existingCustomer.getAddress().put(type, address);
                }
            }
        }

        // Save the updated customer
        customerDao.save(existingCustomer);

        return existingCustomer;
    }

    @Override
    public Customer updateCustomerContactNoOrEmail(CustomerUpdateDTO customerUpdateDTO, Long customerId)
            throws CustomerNotFoundException {
        // Check if the customer exists
        Optional<Customer> opt = customerDao.findById(customerId);

        if (opt.isEmpty()) {
            throw new CustomerNotFoundException("Customer does not exist");
        }

        Customer existingCustomer = opt.get();

        // Update customer details
        if (customerUpdateDTO.getEmail() != null) {
            existingCustomer.setEmail(customerUpdateDTO.getEmail());
        }

        existingCustomer.setContactNo(customerUpdateDTO.getContactNo());

        // Save the updated customer
        customerDao.save(existingCustomer);

        return existingCustomer;
    }

    // Method to update customer password
    @Override
    public SessionDTO updateCustomerPassword(Long customerId,
            CustomerDTO customerDTO,
            String currentPassword) {
        // Check if the customer exists
        if (!customerDao.existsById(customerId)) {
            throw new CustomerNotFoundException("Customer does not exist");
        }

        // Retrieve the customer from the database
        Customer customer = customerDao.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer does not exist"));

        // Check if the current password matches the password in the database
        if (!passwordEncoder.matches(currentPassword, customer.getHashedPassword())) {
            throw new LoginException("Current password does not match");
        }

        // Hash and set the new password
        String newPassword = customerDTO.getPassword();
        String hashedPassword = passwordEncoder.encode(newPassword);

        customer.setHashedPassword(hashedPassword);

        // Save the updated customer
        customerDao.save(customer);

        // Return the session DTO (you might need to modify this part based on your
        // logic)
        SessionDTO session = new SessionDTO();
        session.setToken("newToken"); // Set a new token or update the existing one
        session.setMessage("Password updated successfully");

        return session;
    }

    // Method to find address by type
    private Address findAddressByType(Map<String, Address> addressMap, String type) {
        return addressMap.get(type);
    }

    // Method to update Customer address
    @Override
    public Customer updateAddress(CustomerUpdateDTO customer, Long customerId, Address address, String type) throws CustomerException {

        // Find the customer by ID
        Optional<Customer> opt = customerDao.findById(customerId);

        // Check if the customer exists
        if (opt.isEmpty()) {
            throw new CustomerNotFoundException("Customer does not exist");
        }

        // Retrieve the customer from the database
        Customer existingCustomer = opt.get();

        // Update the address for the specified type
        // Assuming you have a method to find the specific address in the list
        Address existingAddress = findAddressByType(existingCustomer.getAddress(), type);

        // Update or add the new address
        if (existingAddress != null) {
            // Update the existing address
            existingAddress.setBlockNumber(address.getBlockNumber());
            existingAddress.setStreetName(address.getStreetName());
            existingAddress.setBuildingName(address.getBuildingName());
            existingAddress.setCity(address.getCity());
            existingAddress.setState(address.getState());
            existingAddress.setPostalCode(address.getPostalCode());
        } else {
            // Add the new address
            existingCustomer.getAddress().put(type, address);
        }

        existingCustomer.setContactNo(customer.getContactNo());

        // Save the updated customer
        customerDao.save(existingCustomer);
        // Save the updated customer
        return customerDao.save(existingCustomer);
    }

    // Method to delete customer
    @Override
    public void deleteCustomer(Long customerId) throws CustomerNotFoundException {

        // Check if the customer exists
        if (!customerDao.existsById(customerId)) {
            throw new CustomerNotFoundException("Customer does not exist");
        }

        // If the customer exists, delete the customer
        customerDao.deleteById(customerId);

        // Check if the customer still exists
        if (customerDao.existsById(customerId)) {
            throw new CustomerException("Customer could not be deleted");
        }
    }

    // Method to delete customer by ID
    @Override
    public SessionDTO deleteCustomerById(Long customerId, String token) throws CustomerNotFoundException {
        // Check if the customer exists
        Optional<Customer> opt = customerDao.findById(customerId);

        if (opt.isEmpty()) {
            throw new CustomerNotFoundException("Customer does not exist");
        }

        // Retrieve the customer from the database
        Customer existingCustomer = opt.get();

        // Create a new session DTO
        SessionDTO session = new SessionDTO();

        // Token property of the session DTO is set to logout the customer
        session.setToken(token);

        // Delete existing customer from the database
        customerDao.delete(existingCustomer);

        // Logout the customer
        loginService.logoutCustomer(session);

        // set message property of the session DTO
        session.setMessage("Deleted account and logged out successfully");

        // Return the session DTO
        return session;
    }

    // Method to delete customer by contact number or email
    @Override
    public SessionDTO deleteCustomerByContactNoOrEmail(String contactNo, String email, String token)
            throws CustomerNotFoundException {

        // Check if the token contains "customer"
        if (!token.contains("customer")) {

            // If the token does not contain "customer", throw a login exception
            throw new LoginException("Invalid session token for customer");
        }

        // Check the status of the token using the login service
        loginService.checkTokenStatus(token);

        // Find the customer by contact number or email
        Optional<Customer> opt = customerDao.findByContactNoOrEmail(contactNo, email);

        // Check if the customer exists
        if (opt.isEmpty()) {
            throw new CustomerNotFoundException("Customer does not exist");
        }

        // Retrieve the customer from the database
        Customer existingCustomer = opt.get();

        // Create a new session DTO
        SessionDTO session = new SessionDTO();

        // Token property of the session DTO is set to logout the customer
        session.setToken(token);

        // Perform the deletion
        customerDao.delete(existingCustomer);

        // Logout the customer
        loginService.logoutCustomer(session);

        // Set message property of the session DTO
        session.setMessage("Deleted account and logged out successfully");
        return session;
    }
}
