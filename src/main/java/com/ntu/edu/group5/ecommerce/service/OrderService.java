package com.ntu.edu.group5.ecommerce.service;

import java.util.ArrayList;

import com.ntu.edu.group5.ecommerce.entity.*;

public interface OrderService {


    Order createOrder (int quantity, long customerId, long productId);

    Order getOrder(long id);

    ArrayList<Order> getAllOrders();


}
