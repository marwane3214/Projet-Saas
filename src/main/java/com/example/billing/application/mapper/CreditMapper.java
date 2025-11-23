package com.example.billing.application.mapper;

import com.example.billing.application.dto.CreditDTO;
import com.example.billing.domain.Credit;
import org.springframework.stereotype.Component;

@Component
public class CreditMapper {
    
    public CreditDTO toDTO(Credit credit) {
        return CreditDTO.builder()
                .id(credit.getId())
                .customerId(credit.getCustomerId())
                .invoiceId(credit.getInvoiceId())
                .amount(credit.getAmount())
                .reason(credit.getReason())
                .createdAt(credit.getCreatedAt())
                .applied(credit.getApplied())
                .build();
    }
}

