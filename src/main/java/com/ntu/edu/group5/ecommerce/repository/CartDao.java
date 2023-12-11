package com.ntu.edu.group5.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import com.ntu.edu.group5.ecommerce.entity.Cart;

@Repository
public interface CartDao extends JpaRepository<Cart, Long> {

    Optional<Cart> findById(Long id);
}
