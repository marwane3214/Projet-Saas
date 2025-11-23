package com.customer.service.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequest {
    @NotBlank(message = "Legal name is required")
    private String legalName;
    
    @NotBlank(message = "VAT number is required")
    @Pattern(regexp = "^[A-Z]{2}[A-Z0-9]{2,12}$", message = "VAT number must be valid (e.g., FR12345678901)")
    private String vatNumber;
    
    @NotBlank(message = "Country is required")
    private String country;
}

