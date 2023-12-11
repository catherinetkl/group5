package com.ntu.edu.group5.ecommerce.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Entity
@Transactional
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @NotBlank(message = "First name is mandatory")
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;

    @NotNull(message = "Please enter the password")
    @Column(name = "hashed_password")
    private String hashedPassword;

    @NotNull(message = "Contact number should be 8 digits")
    @Column(name = "contact_no", unique = true)
    @Pattern(regexp = "[689]{1}[0-9]{7}", message = "Enter a valid 8 digit contact number")
    private String contactNo;

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "customer_address_mapping", joinColumns = {
            @JoinColumn(name = "customer_id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "address_id")
    })
    private Map<String, Address> address = new HashMap<>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Address> addresses;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String contactNo) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNo = contactNo;
    }

    // Method to set hashed password using BCrypt
    public void setHashedPassword(String plainPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.hashedPassword = passwordEncoder.encode(plainPassword);
    }

    // Establishing Customer - Order relationship
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "customer")
    private List<Order> orders;

    // Establishing Customer - Cart relationship
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    public void setOrders(ArrayList<Order> arrayList) {
    }

}
