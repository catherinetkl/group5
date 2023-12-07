package com.ntu.edu.group5.ecommerce.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 *
 *  !!! This is a Dummy Product class used to test the Order class. Not to be confused with Product.java !!
 *  To be deleted upon succesful integration.
 *
 */


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name= "dummyproduct")
public class OrderDummyP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long productId;

    @Column(name = "dummy_product_name")
    private String name;

    @Column(name = "dummy_category_id")
    private String category;

    @OneToMany(mappedBy = "orderedProduct", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Order> orders;

    public OrderDummyP(String name, String category) {
        this.name = name;
        this.category = category;
    }

}
