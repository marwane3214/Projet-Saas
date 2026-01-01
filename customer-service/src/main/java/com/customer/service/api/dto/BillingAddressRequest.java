package com.customer.service.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingAddressRequest {
    @NotBlank(message = "Address line 1 is required")
    private String line1;
    
    private String line2;
    
    @NotBlank(message = "City is required")
    private String city;
    
    @NotBlank(message = "Zip code is required")
    private String zipCode;
    
    @NotBlank(message = "Country is required")
    private String country;
}

