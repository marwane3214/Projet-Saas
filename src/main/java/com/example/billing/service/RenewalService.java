package com.example.billing.service;

import com.example.billing.application.dto.RenewalDTO;
import com.example.billing.application.dto.ScheduleRenewalRequest;
import com.example.billing.application.mapper.RenewalMapper;
import com.example.billing.domain.Renewal;
import com.example.billing.infrastructure.kafka.KafkaEventProducer;
import com.example.billing.infrastructure.kafka.event.RenewalProcessedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.billing.repository.RenewalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RenewalService {
    
    private final RenewalRepository renewalRepository;
    private final RenewalMapper renewalMapper;
    private final InvoiceService invoiceService;
    @Autowired(required = false)
    private KafkaEventProducer kafkaEventProducer;
    private static final int MAX_RETRIES = 3;
    
    public RenewalDTO scheduleRenewal(Long subscriptionId, LocalDate renewalDate) {
        Renewal renewal = Renewal.builder()
                .subscriptionId(subscriptionId)
                .renewalDate(renewalDate)
                .status(Renewal.RenewalStatus.SCHEDULED)
                .createdAt(LocalDateTime.now())
                .retryCount(0)
                .build();
        
        renewal = renewalRepository.save(renewal);
        log.info("Scheduled renewal for subscription {} on {}", subscriptionId, renewalDate);
        
        return renewalMapper.toDTO(renewal);
    }
    
    public RenewalDTO scheduleRenewal(ScheduleRenewalRequest request) {
        return scheduleRenewal(request.getSubscriptionId(), request.getRenewalDate());
    }
    
    public RenewalDTO processRenewal(Long renewalId) {
        Renewal renewal = renewalRepository.findById(renewalId)
                .orElseThrow(() -> new IllegalArgumentException("Renewal not found: " + renewalId));
        
        if (renewal.getStatus() == Renewal.RenewalStatus.PROCESSED) {
            log.warn("Renewal {} already processed", renewalId);
            return renewalMapper.toDTO(renewal);
        }
        
        try {
            log.info("Processing renewal {} for subscription {}", renewalId, renewal.getSubscriptionId());
            
            // In production, this would fetch subscription details from another service
            // For now, we'll create an invoice for the renewal
            // This is a simplified version - in production, you'd call a subscription service
            
            renewal.setStatus(Renewal.RenewalStatus.PROCESSED);
            renewal.setProcessedAt(LocalDateTime.now());
            renewal = renewalRepository.save(renewal);
            
            // Publish event (if Kafka is available)
            if (kafkaEventProducer != null) {
                RenewalProcessedEvent event = RenewalProcessedEvent.builder()
                        .renewalId(renewal.getId().toString())
                        .subscriptionId(renewal.getSubscriptionId())
                        .status(renewal.getStatus().name())
                        .renewalDate(renewal.getRenewalDate())
                        .processedAt(renewal.getProcessedAt())
                        .build();
                
                kafkaEventProducer.publishRenewalProcessed(event);
            }
            log.info("Renewal {} processed successfully", renewalId);
            
            return renewalMapper.toDTO(renewal);
            
        } catch (Exception e) {
            log.error("Failed to process renewal {}", renewalId, e);
            renewal.setStatus(Renewal.RenewalStatus.FAILED);
            renewal.setFailureReason(e.getMessage());
            renewal.setRetryCount((renewal.getRetryCount() == null ? 0 : renewal.getRetryCount()) + 1);
            renewalRepository.save(renewal);
            throw new RuntimeException("Failed to process renewal: " + e.getMessage(), e);
        }
    }
    
    public List<RenewalDTO> processDueRenewals() {
        LocalDate today = LocalDate.now();
        List<Renewal> dueRenewals = renewalRepository.findDueRenewals(today, Renewal.RenewalStatus.SCHEDULED);
        
        log.info("Found {} renewals due for processing", dueRenewals.size());
        
        return dueRenewals.stream()
                .map(renewal -> {
                    try {
                        return processRenewal(renewal.getId());
                    } catch (Exception e) {
                        log.error("Error processing renewal {}", renewal.getId(), e);
                        return renewalMapper.toDTO(renewal);
                    }
                })
                .collect(Collectors.toList());
    }
    
    public List<RenewalDTO> retryFailedRenewals() {
        List<Renewal> failedRenewals = renewalRepository.findFailedRenewalsForRetry(
                Renewal.RenewalStatus.FAILED, MAX_RETRIES);
        
        log.info("Found {} failed renewals to retry", failedRenewals.size());
        
        return failedRenewals.stream()
                .map(renewal -> {
                    try {
                        // Reset status to scheduled for retry
                        renewal.setStatus(Renewal.RenewalStatus.SCHEDULED);
                        renewal.setFailureReason(null);
                        renewal = renewalRepository.save(renewal);
                        return processRenewal(renewal.getId());
                    } catch (Exception e) {
                        log.error("Error retrying renewal {}", renewal.getId(), e);
                        return renewalMapper.toDTO(renewal);
                    }
                })
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public RenewalDTO getRenewalById(Long id) {
        Renewal renewal = renewalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Renewal not found: " + id));
        return renewalMapper.toDTO(renewal);
    }
    
    @Transactional(readOnly = true)
    public List<RenewalDTO> getRenewalsBySubscription(Long subscriptionId) {
        return renewalRepository.findBySubscriptionId(subscriptionId).stream()
                .map(renewalMapper::toDTO)
                .collect(Collectors.toList());
    }
}
