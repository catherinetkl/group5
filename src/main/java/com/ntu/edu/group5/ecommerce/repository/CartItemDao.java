package com.ntu.edu.group5.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ntu.edu.group5.ecommerce.entity.CartItem;

public interface CartItemDao extends JpaRepository<CartItem, Long>{
    @Query("SELECT c FROM Cart c WHERE c.id = :id")
    Optional<CartItem> findById(@Param("id") Long id);

    List<CartItem> findCartByCustomerId(Long customerId);

}
