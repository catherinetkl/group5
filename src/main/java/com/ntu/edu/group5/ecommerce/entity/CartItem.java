package com.ntu.edu.group5.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartItemId;

    private Long customerId;
    
    @ManyToOne
    @JsonIgnoreProperties(value = {
            "cartItems"
    })
    private Product cartProduct;

    @ManyToOne
    @JsonIgnore
    private Cart cart;

    @ManyToOne
    @JsonIgnore
    private Order order;

    private Integer cartItemQuantity;

}
