package com.example.billing.application.mapper;

import com.example.billing.application.dto.PaymentDTO;
import com.example.billing.domain.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    
    public PaymentDTO toDTO(Payment payment) {
        return PaymentDTO.builder()
                .id(payment.getId())
                .invoiceId(payment.getInvoiceId())
                .amount(payment.getAmount())
                .method(payment.getMethod())
                .gatewayReference(payment.getGatewayReference())
                .status(payment.getStatus())
                .createdAt(payment.getCreatedAt())
                .failureReason(payment.getFailureReason())
                .build();
    }
}

