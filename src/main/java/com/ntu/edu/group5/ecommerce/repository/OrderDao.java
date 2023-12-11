package com.ntu.edu.group5.ecommerce.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ntu.edu.group5.ecommerce.exception.CustomerNotFoundException;
import com.ntu.edu.group5.ecommerce.exception.OrderException;
import com.ntu.edu.group5.ecommerce.entity.CartItem;
import com.ntu.edu.group5.ecommerce.entity.Customer;
import com.ntu.edu.group5.ecommerce.entity.Order;
import com.ntu.edu.group5.ecommerce.entity.Product;

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {
	public List<Order> findByDate(LocalDate date);

    @Query("select c.orders from Customer c where c.customerId = customerId")
    public List<Order> getListOfOrdersByCustomerid(@Param("customerId") Long customerId);

	@Query("select c from Customer c where c.customerId = customerId")
	public Customer getCustomerByOrderid(@Param("customerId") Long customerId);

    public List<Product> getListOfProductsByOrderId(Long orderId);

    @Query("update Order o set o.orderStatus = OrderStatusValues.CANCELLED WHERE o.orderId = orderId ")
    public Order cancelOrderByOrderId(@Param("orderId") Long orderId);
}
