package com.ntu.edu.group5.ecommerce.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ntu.edu.group5.ecommerce.service.OrderServiceImpl;
import com.ntu.edu.group5.ecommerce.entity.*;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderServiceImpl orderServ;

    @Autowired
    public OrderController(OrderServiceImpl orderServ){
        this.orderServ = orderServ;
    }

    //tested ok
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody TemplateOrder dataOrder) {
        Order newOrder = orderServ.createOrder(dataOrder.getQuantity(), dataOrder.getCustomerId(),dataOrder.getProductId());
        System.out.println("🔵 POST-CONTROLLER 🔵  newOrder " + newOrder);
        return new ResponseEntity<>(newOrder, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity< ArrayList<Order>> getAllOrders(){
        ArrayList<Order> ordersList = orderServ.getAllOrders();
        return new ResponseEntity<>(ordersList , HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable long orderId){
        Order foundOrder = orderServ.getOrder(orderId);
        System.out.println("🟣 GET-CONTROLLER 🟣 foundOrder " + foundOrder);
        return new ResponseEntity<>(foundOrder, HttpStatus.CREATED);
    }

}

class TemplateOrder{

    long customerId;
    long productId;
    int quantity;
    public long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
    public long getProductId() {
        return productId;
    }
    public void setProductId(long productId) {
        this.productId = productId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}