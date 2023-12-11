package com.ntu.edu.group5.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ntu.edu.group5.ecommerce.entity.Address;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    // Custom query to find all addresses with a certain postalCode
    List<Address> findByPostalCode(String postalCode);
}
