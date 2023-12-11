package com.ntu.edu.group5.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ntu.edu.group5.ecommerce.entity.Seller;

import java.util.List;


public interface SellerRepository extends JpaRepository<Seller, Long> {

    List<Seller> findByFirstName(String firstName);
} 