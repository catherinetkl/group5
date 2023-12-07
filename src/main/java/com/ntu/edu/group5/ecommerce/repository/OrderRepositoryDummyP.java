package com.ntu.edu.group5.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ntu.edu.group5.ecommerce.entity.*;

import java.util.Optional;

/*
 *
 *  !!! This is a Dummy Product Repo class used to test the Order  Repo class. Not to be confused with Product.java !!
 *  To be deleted upon succesful integration.
 *
 */


@Repository
public interface OrderRepositoryDummyP extends JpaRepository <OrderDummyP,Long> {
    Optional<OrderDummyP> findByProductId(long productId);

}