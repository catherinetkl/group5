package com.ntu.edu.group5.ecommerce.entity;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class CustomerUpdateDTO {

    @Pattern(regexp = "[A-Za-z.\\s]+", message = "Enter valid characters in first name")
    private String firstName;

    @Pattern(regexp = "[A-Za-z.\\s]+", message = "Enter valid characters in last name")
    private String lastName;

    @NotNull(message = "Please enter the contact Number")
    @Pattern(regexp = "[689]{1}[0-9]{7}", message = "Enter valid 8 digit contact number")
    private String contactNo;

    @Column(unique = true)
    @Email
    private String email;

    @Pattern(regexp = "[A-Za-z0-9!@#$%^&*_]{8,15}", message = "Password must be 8-15 characters in length and can include A-Z, a-z, 0-9, or special characters !@#$%^&*_")
    private String password;

    private Map<String, Address> address = new HashMap<>();

}
