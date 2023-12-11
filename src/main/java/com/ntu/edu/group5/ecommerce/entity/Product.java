package com.ntu.edu.group5.ecommerce.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {

    @Id
    private Long productId;

    @NotNull
    private String productName;

    @NotNull
    private String description;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private double price;

    @NotNull
	private String manufacturer;

    @NotNull
    @Min(value = 0)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Seller seller;

    @OneToMany(mappedBy = "cartProduct", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

    @ManyToMany
    @JsonIgnore
    private List<Cart> productCarts = new ArrayList<>();

    public Product(Long productId, String productName, String description, double price, String manufacturer, Integer quantity, CategoryEnum category, ProductStatus status, Seller seller) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.category = category;
        this.status = status;
        this.seller = seller;
    }
}
