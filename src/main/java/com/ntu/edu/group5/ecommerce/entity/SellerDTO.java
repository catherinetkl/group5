package com.ntu.edu.group5.ecommerce.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class SellerDTO {


	@NotNull(message="Please enter your Contact Number")
	@Pattern(regexp="[689]{1}[0-9]{7}", message="Enter a valid Contact Number")
	private String contactNo;


	@Pattern(regexp="[A-Za-z0-9!@#$%^&*_]{8,15}", message="Please Enter a valid Password")
	private String password;

}
