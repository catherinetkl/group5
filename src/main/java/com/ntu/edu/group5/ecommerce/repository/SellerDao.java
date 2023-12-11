package com.ntu.edu.group5.ecommerce.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ntu.edu.group5.ecommerce.entity.Seller;

public interface SellerDao extends JpaRepository<Seller, Long> {
    // Query to find sellers by id
    Optional<Seller> findById(Long id);

    // Custom query to find the seller with first name or last name
    List<Seller> findByFirstNameOrLastName(String firstName, String lastName);

    // Custom query to find the seller with a certain email
    Optional<Seller> findByEmail(String email);

    // Custom query to find the seller with a certain contact number
    Optional<Seller> findByContactNo(String contactNo);

    // Custom query to find the seller with a certain contact number or email
    Optional<Seller> findByContactNoOrEmail(String contactNo, String email);

}
