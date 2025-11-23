package com.example.billing.application.dto;

import com.example.billing.domain.Invoice;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {
    private Long id;
    
    @NotBlank(message = "Invoice number is required")
    private String number;
    
    @NotNull(message = "Customer ID is required")
    private Long customerId;
    
    @NotNull(message = "Subscription ID is required")
    private Long subscriptionId;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be positive")
    private BigDecimal amount;
    
    @NotBlank(message = "Currency is required")
    @Size(min = 3, max = 3, message = "Currency must be 3 characters")
    private String currency;
    
    private Invoice.InvoiceStatus status;
    
    @NotNull(message = "Due date is required")
    private LocalDate dueDate;
    
    private LocalDateTime issuedAt;
    
    private String notes;
}

