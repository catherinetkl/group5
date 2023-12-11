package com.ntu.edu.group5.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	private String productName;
	private String manufaturer;
	private Double price;
	private Integer quantity;
}
