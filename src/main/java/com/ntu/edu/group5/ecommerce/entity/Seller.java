package com.ntu.edu.group5.ecommerce.entity;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sellerId;

    @NotNull(message = "Please enter the first name")
    @Pattern(regexp = "[A-Za-z\\s]+", message = "First Name should contains alphabets only")
    private String firstName;

    @NotNull(message = "Please enter the last name")
    @Pattern(regexp = "[A-Za-z\\s]+", message = "First Name should contains alphabets only")
    private String lastName;

    @NotNull(message = "Please enter your contact number")
    @Pattern(regexp = "[689]{1}[0-9]{7}", message = "Enter a valid contact number")
    @Column(unique = true)
    private String contactNo;

    @Email
    @Column(unique = true)
    private String email;

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @NotNull(message = "Please enter the password")
    @Column(name = "hashed_password")
    private String hashedPassword;

    // Method to set hashed password using BCrypt
    public void setHashedPassword(String plainPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.hashedPassword = passwordEncoder.encode(plainPassword);
    }

    @OneToMany
    @JsonIgnore
    private List<Product> product;
}
