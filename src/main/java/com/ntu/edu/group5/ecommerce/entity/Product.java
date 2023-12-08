package com.ntu.edu.group5.ecommerce.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "product_id")
    final private String productId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    // For now I just created a new order_id column. But later once combine nidda
    // map to order table
    @Column(name = "order_id")
    private String orderId;

    public Product() {
        this.productId = UUID.randomUUID().toString();
        this.orderId = UUID.randomUUID().toString();
    }

    public Product(String name, String description, double price) {
        this();
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
