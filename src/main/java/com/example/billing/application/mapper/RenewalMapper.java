package com.example.billing.application.mapper;

import com.example.billing.application.dto.RenewalDTO;
import com.example.billing.domain.Renewal;
import org.springframework.stereotype.Component;

@Component
public class RenewalMapper {
    
    public RenewalDTO toDTO(Renewal renewal) {
        return RenewalDTO.builder()
                .id(renewal.getId())
                .subscriptionId(renewal.getSubscriptionId())
                .renewalDate(renewal.getRenewalDate())
                .status(renewal.getStatus())
                .createdAt(renewal.getCreatedAt())
                .processedAt(renewal.getProcessedAt())
                .failureReason(renewal.getFailureReason())
                .retryCount(renewal.getRetryCount())
                .build();
    }
}

