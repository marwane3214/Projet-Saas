package com.example.billing.application.dto;

import com.example.billing.domain.Renewal;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RenewalDTO {
    private Long id;
    
    @NotNull(message = "Subscription ID is required")
    private Long subscriptionId;
    
    @NotNull(message = "Renewal date is required")
    private LocalDate renewalDate;
    
    private Renewal.RenewalStatus status;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime processedAt;
    
    private String failureReason;
    
    private Integer retryCount;
}

