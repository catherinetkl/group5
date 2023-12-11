package com.ntu.edu.group5.ecommerce.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;


import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer id;

    @NotBlank(message = "Block number is mandatory")
    @Column(name = "block_number")
    private String blockNumber;

    @NotBlank(message = "Street name is mandatory")
    @Column(name = "street_name")
    private String streetName;

    @NotBlank(message = "City is mandatory")
    @Column(name = "building_name")
    private String buildingName;

    
    @Column(name = "city")
    private String city;

    @NotBlank(message = "State is mandatory")
    @Column(name = "state")
    private String state;

    @NotBlank(message = "Postal code is mandatory")
    @Digits(integer = 6, fraction = 0, message = "Postal code should be 6 digits")
    @Column(name = "postal_code")
    private String postalCode;
    
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(mappedBy = "shippingAddress")
    @JsonIgnore
    private Order order;

    // Constructors

    public Address() {
      
    }

    public Address(String blockNumber, String streetName, String buildingName, String city, String state, String postalCode, Customer customer, Order order) {
        this.blockNumber = blockNumber;
        this.streetName = streetName;
        this.buildingName = buildingName;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.customer = customer;
        this.order = order;
    }

    
}
