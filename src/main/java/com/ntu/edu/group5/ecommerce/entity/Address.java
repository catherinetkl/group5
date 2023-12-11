package com.ntu.edu.group5.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {

    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long addressId;

    @NotNull(message = "Block number cannot be null")
    @Pattern(regexp = "^[0-9]{1,3}[A-Z]?$", message = "Not a valid block number")
    private String blockNumber;

    @NotNull(message = "Street name cannot be null")
    @Pattern(regexp = "[A-Za-z0-9\\s-]+", message = "Not a valid street name")
    private String streetName;

    @Nullable
    private String buildingName;

    @NotNull(message = "City name cannot be null")
    @Pattern(regexp = "[A-Za-z\\s]+", message = "Not a valid city name")
    private String city;

    @NotNull(message = "State name cannot be null")
    private String state;

    @NotNull(message = "Postal code cannot be null")
    @Pattern(regexp = "[0-9]{6}", message = "Postal code not valid. Must be 6 digits")
    private String postalCode;

    @ManyToOne(fetch= FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
