package com.ntu.edu.group5.ecommerce.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ntu.edu.group5.ecommerce.entity.*;
import com.ntu.edu.group5.ecommerce.repository.*;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    private CustomerRepository customerRepo;
    private OrderRepository orderRepo;
    //a dummy product repo only for Order testing purpose. not to be confused with ProductRepo
    private OrderRepositoryDummyP productRepo;

    @Autowired
    public OrderServiceImpl(CustomerRepository customerRepo,
        OrderRepository orderRepo ,OrderRepositoryDummyP productRepo){
        this.customerRepo = customerRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    @Override
    @Transactional
    public Order createOrder (int quantity, long customerId, long productId){

        Customer foundCustomer = customerRepo.findById(customerId)
            .orElseThrow(()-> new RuntimeException("Cannot find customer " + customerId));
        //System.out.println("🔵foundCustomer " + foundCustomer.getFirstName() + " customerId " + customerId);
            OrderDummyP foundProduct = productRepo.findById(productId)
            .orElseThrow(()-> new RuntimeException("Cannot find product " + productId));
        //System.out.println("🔵foundProduct " + foundProduct.getName() + " productId " + productId);
        Order newOrder = new Order();
        //System.out.println("🔵quantity " + quantity);
        newOrder.setOrderingCustomer(foundCustomer);
        //System.out.println("🔵newOrder set orderingCustomer" + newOrder.getOrderingCustomer().getFirstName());
        newOrder.setOrderedProduct(foundProduct);
        //System.out.println("🔵newOrder set foundProduct" + newOrder.getOrderedProduct().getName());
        newOrder.setOrderedQuantity(quantity);
        //System.out.println("🔵newOrder set quantity " + newOrder.getOrderedQuantity());
        try {
            orderRepo.save(newOrder);
            //System.out.println("🔵saved newOrder " + newOrder);
        }
        catch (DataAccessException e){
            e.printStackTrace();
            throw new RuntimeException("Error saving order",e);
        }
        return newOrder;
    }

    @Override
    public Order getOrder(long id){
        Order foundOrder =orderRepo.findById(id).orElseThrow(()-> new RuntimeException("Order not found"){});
        // System.out.println("🟣 foundOrder " + foundOrder.getOrderId());
        // Customer foundCustomer = customerRepo.getReferenceById(foundOrder.getCustomerId());
        // System.out.println("🟣 foundCustomer " + foundCustomer.getFirstName());
        // OrderDummyP foundProduct = productRepo.getReferenceById(foundOrder.getProductId());
        // System.out.println("🟣 foundProduct " + foundProduct.getName());
        // foundOrder.setOrderedProduct(foundProduct);
        // foundOrder.setOrderingCustomer(foundCustomer);
        // System.out.println("🟣 foundOrder setfoundProduct & foundCustomer : " + foundOrder.getOrderedProduct().getName() + " and "
        //     + foundOrder.getOrderingCustomer().getFirstName());

        foundOrder.getOrderingCustomer().getId();
        foundOrder.getOrderedProduct().getProductId();

        return foundOrder;
    }

    @Override
    public ArrayList<Order> getAllOrders(){
        ArrayList<Order> getAllOrders= new ArrayList<>();
        getAllOrders = (ArrayList<Order>) orderRepo.findAll();
        return getAllOrders;
    }

    @PostConstruct
    public void loadData(){
        //load 3 customers into server
        Customer first = new Customer("Bruce", "Banner", "12345678", 1972);
        customerRepo.save(first);
        Customer second =new Customer("Peter","Parker", "12345677", 2005);
        customerRepo.save(second);
        Customer third = new Customer("Stephen", "Strange", "12345678", 1976);
        customerRepo.save(third);
        //load 3 products into server
        OrderDummyP one =new OrderDummyP("Lenovo","Laptop");
        productRepo.save(one);
        OrderDummyP two = new OrderDummyP("Surface Pro","Tablet PC");
        productRepo.save(two);
        OrderDummyP three = new OrderDummyP("Macbook" ,"Apple Product");
        productRepo.save(three);
        //create and load 3 orders into server
        createOrder(1,1,1 );
        createOrder(2,2,2 );
        createOrder(3,3,3 );
    }

}
