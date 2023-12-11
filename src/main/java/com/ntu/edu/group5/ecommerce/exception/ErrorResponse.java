package com.ntu.edu.group5.ecommerce.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private String details;
}
