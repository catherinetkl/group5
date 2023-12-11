package com.ntu.edu.group5.ecommerce.entity;

import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartDTO {

	@NotNull
	private Long productId;

	private String productName;

	private Double price;

	@Min(1)
	private Integer quantity;

}
