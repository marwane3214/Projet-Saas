package com.example.billing.infrastructure.kafka;

import com.example.billing.infrastructure.kafka.event.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
@org.springframework.boot.autoconfigure.condition.ConditionalOnProperty(name = "spring.kafka.bootstrap-servers")
public class KafkaEventProducer {
    
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    
    private static final String TOPIC_INVOICE_CREATED = "invoice-created";
    private static final String TOPIC_PAYMENT_PROCESSED = "payment-processed";
    private static final String TOPIC_RENEWAL_PROCESSED = "renewal-processed";
    
    public void publishInvoiceCreated(InvoiceCreatedEvent event) {
        event.setEventId(UUID.randomUUID().toString());
        event.setTimestamp(LocalDateTime.now());
        publishEvent(TOPIC_INVOICE_CREATED, event.getInvoiceId(), event);
    }
    
    public void publishPaymentProcessed(PaymentProcessedEvent event) {
        event.setEventId(UUID.randomUUID().toString());
        event.setTimestamp(LocalDateTime.now());
        publishEvent(TOPIC_PAYMENT_PROCESSED, event.getPaymentId(), event);
    }
    
    public void publishRenewalProcessed(RenewalProcessedEvent event) {
        event.setEventId(UUID.randomUUID().toString());
        event.setTimestamp(LocalDateTime.now());
        publishEvent(TOPIC_RENEWAL_PROCESSED, event.getRenewalId(), event);
    }
    
    private void publishEvent(String topic, String key, Object event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, message);
            
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Successfully sent event to topic {} with key {}: {}", topic, key, message);
                } else {
                    log.error("Failed to send event to topic {} with key {}", topic, key, ex);
                }
            });
        } catch (JsonProcessingException e) {
            log.error("Error serializing event for topic {}", topic, e);
        }
    }
}

