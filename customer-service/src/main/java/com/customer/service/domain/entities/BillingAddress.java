package com.customer.service.domain.entities;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingAddress {
    private UUID id;
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

