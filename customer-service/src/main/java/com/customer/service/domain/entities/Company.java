package com.customer.service.domain.entities;

import com.customer.service.domain.valueobjects.VatNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    private UUID id;
    private String legalName;
    private VatNumber vatNumber;
    private String country;
    private LocalDateTime createdAt;
}

