package com.ntu.edu.group5.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ntu.edu.group5.ecommerce.entity.Customer;

import java.util.List;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Long> {
    // Query to find customers by id
    Optional<Customer> findById(Long id);

    // Custom query to find all customers with a certain first name or last name
    List<Customer> findByFirstNameOrLastName(String firstName, String lastName);

    // Custom query to find the customer with a certain email
    Optional<Customer> findByEmail(String email);

    // Custom query to find the customer with a certain contact number
    Optional<Customer> findByContactNo(String contactNo);

    // Custom query to find the customer with a certain contact number or email
    Optional<Customer> findByContactNoOrEmail(String contactNo, String email);
}
