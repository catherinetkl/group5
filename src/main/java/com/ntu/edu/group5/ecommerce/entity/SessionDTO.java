package com.ntu.edu.group5.ecommerce.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTO {

	private String token;
	private String message;
}
