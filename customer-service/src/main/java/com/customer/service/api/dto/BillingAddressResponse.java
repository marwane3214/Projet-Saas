package com.customer.service.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingAddressResponse {
    private UUID id;
    private String line1;
    private String line2;
    private String city;
    private String zipCode;
    private String country;
}

