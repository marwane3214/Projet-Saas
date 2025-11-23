package com.example.billing.service;

import com.example.billing.application.dto.PaymentDTO;
import com.example.billing.application.dto.RegisterPaymentRequest;
import com.example.billing.application.mapper.PaymentMapper;
import com.example.billing.domain.Invoice;
import com.example.billing.domain.Payment;
import com.example.billing.infrastructure.idempotency.IdempotencyService;
import com.example.billing.infrastructure.kafka.KafkaEventProducer;
import com.example.billing.infrastructure.kafka.event.PaymentProcessedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.billing.repository.InvoiceRepository;
import com.example.billing.repository.PaymentRepository;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentMapper paymentMapper;
    @Autowired(required = false)
    private KafkaEventProducer kafkaEventProducer;
    private final IdempotencyService idempotencyService;
    
    @Retry(name = "paymentProcessing")
    public PaymentDTO registerPayment(RegisterPaymentRequest request) {
        // Check idempotency
        if (request.getIdempotencyKey() != null && !request.getIdempotencyKey().isBlank()) {
            if (idempotencyService.isDuplicate(request.getIdempotencyKey())) {
                String existingId = idempotencyService.getResultId(request.getIdempotencyKey())
                    .orElseThrow(() -> new IllegalStateException("Idempotency key found but no result ID"));
                return paymentMapper.toDTO(paymentRepository.findById(Long.parseLong(existingId))
                    .orElseThrow(() -> new IllegalArgumentException("Payment not found: " + existingId)));
            }
        }
        
        // Validate invoice exists
        Invoice invoice = invoiceRepository.findById(request.getInvoiceId())
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found: " + request.getInvoiceId()));
        
        // Check if payment already exists by gateway reference
        if (request.getGatewayReference() != null && !request.getGatewayReference().isBlank()) {
            paymentRepository.findByGatewayReference(request.getGatewayReference())
                    .ifPresent(p -> {
                        throw new IllegalStateException("Payment with gateway reference already exists: " + request.getGatewayReference());
                    });
        }
        
        Payment payment = Payment.builder()
                .invoiceId(request.getInvoiceId())
                .amount(request.getAmount())
                .method(request.getMethod())
                .gatewayReference(request.getGatewayReference())
                .status(Payment.PaymentStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
        
        payment = paymentRepository.save(payment);
        
        // Record idempotency
        if (request.getIdempotencyKey() != null && !request.getIdempotencyKey().isBlank()) {
            idempotencyService.recordRequest(request.getIdempotencyKey(), payment.getId().toString());
        }
        
        // Process payment (simulate payment gateway call)
        processPayment(payment, invoice);
        
        return paymentMapper.toDTO(payment);
    }
    
    private void processPayment(Payment payment, Invoice invoice) {
        try {
            // Simulate payment processing
            // In production, this would call a payment gateway
            log.info("Processing payment {} for invoice {}", payment.getId(), invoice.getId());
            
            // Simulate success (in production, this would be async)
            payment.setStatus(Payment.PaymentStatus.SUCCESS);
            payment = paymentRepository.save(payment);
            
            // Update invoice status
            invoice.setStatus(Invoice.InvoiceStatus.PAID);
            invoiceRepository.save(invoice);
            
            // Publish event (if Kafka is available)
            if (kafkaEventProducer != null) {
                PaymentProcessedEvent event = PaymentProcessedEvent.builder()
                        .paymentId(payment.getId().toString())
                        .invoiceId(invoice.getId().toString())
                        .customerId(invoice.getCustomerId())
                        .amount(payment.getAmount())
                        .method(payment.getMethod())
                        .gatewayReference(payment.getGatewayReference())
                        .status(payment.getStatus().name())
                        .processedAt(payment.getCreatedAt())
                        .build();
                
                kafkaEventProducer.publishPaymentProcessed(event);
            }
            log.info("Payment {} processed successfully", payment.getId());
            
        } catch (Exception e) {
            log.error("Payment processing failed for payment {}", payment.getId(), e);
            payment.setStatus(Payment.PaymentStatus.FAILED);
            payment.setFailureReason(e.getMessage());
            paymentRepository.save(payment);
            throw e;
        }
    }
    
    public void handleFailedPayment(Long paymentId, String failureReason) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found: " + paymentId));
        
        payment.setStatus(Payment.PaymentStatus.FAILED);
        payment.setFailureReason(failureReason);
        paymentRepository.save(payment);
        
        // Update invoice status if needed
        Invoice invoice = invoiceRepository.findById(payment.getInvoiceId())
                .orElse(null);
        if (invoice != null && invoice.getStatus() == Invoice.InvoiceStatus.OPEN) {
            invoice.setStatus(Invoice.InvoiceStatus.FAILED);
            invoiceRepository.save(invoice);
        }
        
        log.info("Handled failed payment: {}", paymentId);
    }
    
    @Transactional(readOnly = true)
    public PaymentDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found: " + id));
        return paymentMapper.toDTO(payment);
    }
    
    @Transactional(readOnly = true)
    public List<PaymentDTO> getPaymentsByInvoice(Long invoiceId) {
        return paymentRepository.findByInvoiceId(invoiceId).stream()
                .map(paymentMapper::toDTO)
                .collect(Collectors.toList());
    }
}
