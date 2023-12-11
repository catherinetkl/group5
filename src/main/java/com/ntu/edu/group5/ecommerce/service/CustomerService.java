package com.ntu.edu.group5.ecommerce.service;

import java.util.List;

import com.ntu.edu.group5.ecommerce.entity.Address;
import com.ntu.edu.group5.ecommerce.entity.Customer;
import com.ntu.edu.group5.ecommerce.entity.CustomerDTO;
import com.ntu.edu.group5.ecommerce.entity.CustomerUpdateDTO;
import com.ntu.edu.group5.ecommerce.entity.Order;
import com.ntu.edu.group5.ecommerce.entity.SessionDTO;
import com.ntu.edu.group5.ecommerce.exception.CustomerException;
import com.ntu.edu.group5.ecommerce.exception.CustomerNotFoundException;

public interface CustomerService {

    public Customer createCustomer(Customer customer) throws CustomerException;

    public List<Customer> getAllCustomers() throws CustomerNotFoundException;

    public Customer getCustomer(Long customerId) throws CustomerNotFoundException;

    public Customer getCustomerByContactNoOrEmail(String mobileNo, String email) throws CustomerNotFoundException;

    public Customer getLoggedInCustomerDetails(String token) throws CustomerNotFoundException;

    public List<Customer> getAllCustomers(String token) throws CustomerNotFoundException;

    public List<Customer> searchCustomers(String firstName, String lastName) throws CustomerNotFoundException;

    public List<Order> getCustomerOrders(Long customerId) throws CustomerException;

    public Customer updateCustomer(CustomerUpdateDTO customer, Long customerId) throws CustomerNotFoundException;

    public Customer updateCustomerContactNoOrEmail(CustomerUpdateDTO customer, Long customerId) throws CustomerNotFoundException;

    public SessionDTO updateCustomerPassword(Long customerId, CustomerDTO customerDTO, String currentPassword) throws CustomerNotFoundException;

    public Customer updateAddress(CustomerUpdateDTO customer, Long customerId, Address address, String type) throws CustomerException;

    public void deleteCustomer(Long customerId) throws CustomerNotFoundException;

    SessionDTO deleteCustomerById(Long customerId, String token) throws CustomerNotFoundException;

    SessionDTO deleteCustomerByContactNoOrEmail(String mobileNo, String email, String token) throws CustomerNotFoundException;
}
