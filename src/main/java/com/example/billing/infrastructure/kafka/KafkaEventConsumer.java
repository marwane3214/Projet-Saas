package com.example.billing.infrastructure.kafka;

import com.example.billing.infrastructure.kafka.event.*;
import com.example.billing.service.InvoiceService;
import com.example.billing.service.PaymentService;
import com.example.billing.service.RenewalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@org.springframework.boot.autoconfigure.condition.ConditionalOnProperty(name = "spring.kafka.bootstrap-servers")
public class KafkaEventConsumer {
    
    private final ObjectMapper objectMapper;
    private final InvoiceService invoiceService;
    private final PaymentService paymentService;
    private final RenewalService renewalService;
    
    @KafkaListener(topics = "subscription-created", groupId = "billing-service-group")
    public void handleSubscriptionCreated(String message, Acknowledgment acknowledgment) {
        try {
            log.info("Received subscription-created event: {}", message);
            SubscriptionCreatedEvent event = objectMapper.readValue(message, SubscriptionCreatedEvent.class);
            
            // Handle subscription created - could create initial invoice or setup
            log.info("Processing subscription created: subscriptionId={}, customerId={}", 
                    event.getSubscriptionId(), event.getCustomerId());
            
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("Error processing subscription-created event", e);
            // In production, implement retry logic or dead letter queue
        }
    }
    
    @KafkaListener(topics = "renewal-due", groupId = "billing-service-group")
    public void handleRenewalDue(String message, Acknowledgment acknowledgment) {
        try {
            log.info("Received renewal-due event: {}", message);
            RenewalDueEvent event = objectMapper.readValue(message, RenewalDueEvent.class);
            
            // Schedule renewal processing
            renewalService.scheduleRenewal(
                Long.parseLong(event.getSubscriptionId()),
                event.getRenewalDate()
            );
            
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("Error processing renewal-due event", e);
        }
    }
    
    @KafkaListener(topics = "payment-failed", groupId = "billing-service-group")
    public void handlePaymentFailed(String message, Acknowledgment acknowledgment) {
        try {
            log.info("Received payment-failed event: {}", message);
            PaymentFailedEvent event = objectMapper.readValue(message, PaymentFailedEvent.class);
            
            // Handle failed payment - update invoice status, retry logic, etc.
            paymentService.handleFailedPayment(
                Long.parseLong(event.getPaymentId()),
                event.getFailureReason()
            );
            
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("Error processing payment-failed event", e);
        }
    }
}

