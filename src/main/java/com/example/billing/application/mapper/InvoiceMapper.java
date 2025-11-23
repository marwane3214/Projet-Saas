package com.example.billing.application.mapper;

import com.example.billing.application.dto.CreateInvoiceRequest;
import com.example.billing.application.dto.InvoiceDTO;
import com.example.billing.domain.Invoice;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class InvoiceMapper {
    
    public Invoice toEntity(CreateInvoiceRequest request) {
        return Invoice.builder()
                .customerId(request.getCustomerId())
                .subscriptionId(request.getSubscriptionId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .dueDate(request.getDueDate())
                .notes(request.getNotes())
                .status(Invoice.InvoiceStatus.DRAFT)
                .issuedAt(LocalDateTime.now())
                .number(generateInvoiceNumber())
                .build();
    }
    
    public InvoiceDTO toDTO(Invoice invoice) {
        return InvoiceDTO.builder()
                .id(invoice.getId())
                .number(invoice.getNumber())
                .customerId(invoice.getCustomerId())
                .subscriptionId(invoice.getSubscriptionId())
                .amount(invoice.getAmount())
                .currency(invoice.getCurrency())
                .status(invoice.getStatus())
                .dueDate(invoice.getDueDate())
                .issuedAt(invoice.getIssuedAt())
                .notes(invoice.getNotes())
                .build();
    }
    
    private String generateInvoiceNumber() {
        return "INV-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}

