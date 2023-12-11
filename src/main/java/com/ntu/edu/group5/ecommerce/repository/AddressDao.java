package com.ntu.edu.group5.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ntu.edu.group5.ecommerce.entity.Address;

@Repository
public interface AddressDao extends JpaRepository<Address, Long>{
    @Query("SELECT a FROM Address a WHERE a.addressId = :addressId")
    Optional<Address> findByAddressId(@Param("addressId") Long addressId);

    @Query("SELECT a FROM Address a WHERE a.customer.customerId = :customerId")
    Optional<Address> findByCustomerId(@Param("customerId") Long customerId);
}
