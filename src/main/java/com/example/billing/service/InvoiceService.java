package com.example.billing.service;

import com.example.billing.application.dto.CreateInvoiceRequest;
import com.example.billing.application.dto.InvoiceDTO;
import com.example.billing.application.mapper.InvoiceMapper;
import com.example.billing.domain.Invoice;
import com.example.billing.infrastructure.idempotency.IdempotencyService;
import com.example.billing.infrastructure.kafka.KafkaEventProducer;
import com.example.billing.infrastructure.kafka.event.InvoiceCreatedEvent;
import com.example.billing.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class InvoiceService {
    
    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;
    @Autowired(required = false)
    private KafkaEventProducer kafkaEventProducer;
    private final IdempotencyService idempotencyService;
    
    public InvoiceDTO createInvoice(CreateInvoiceRequest request) {
        try {
            log.debug("Creating invoice for customer: {}, subscription: {}", 
                    request.getCustomerId(), request.getSubscriptionId());
            
            // Check idempotency
            if (request.getIdempotencyKey() != null && !request.getIdempotencyKey().isBlank()) {
                if (idempotencyService.isDuplicate(request.getIdempotencyKey())) {
                    String existingId = idempotencyService.getResultId(request.getIdempotencyKey())
                        .orElseThrow(() -> new IllegalStateException("Idempotency key found but no result ID"));
                    return invoiceMapper.toDTO(invoiceRepository.findById(Long.parseLong(existingId))
                        .orElseThrow(() -> new IllegalArgumentException("Invoice not found: " + existingId)));
                }
            }
            
            Invoice invoice = invoiceMapper.toEntity(request);
            invoice.setStatus(Invoice.InvoiceStatus.OPEN);
            invoice = invoiceRepository.save(invoice);
            
            log.debug("Invoice saved with ID: {}", invoice.getId());
        
        // Record idempotency
        if (request.getIdempotencyKey() != null && !request.getIdempotencyKey().isBlank()) {
            idempotencyService.recordRequest(request.getIdempotencyKey(), invoice.getId().toString());
        }
        
        // Publish event (if Kafka is available)
        if (kafkaEventProducer != null) {
            InvoiceCreatedEvent event = InvoiceCreatedEvent.builder()
                    .invoiceId(invoice.getId().toString())
                    .invoiceNumber(invoice.getNumber())
                    .customerId(invoice.getCustomerId())
                    .subscriptionId(invoice.getSubscriptionId())
                    .amount(invoice.getAmount())
                    .currency(invoice.getCurrency())
                    .status(invoice.getStatus().name())
                    .issuedAt(invoice.getIssuedAt())
                    .dueDate(invoice.getDueDate())
                    .build();
            
            kafkaEventProducer.publishInvoiceCreated(event);
        }
        log.info("Created invoice: {}", invoice.getNumber());
        
        return invoiceMapper.toDTO(invoice);
        } catch (Exception e) {
            log.error("Error creating invoice: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create invoice: " + e.getMessage(), e);
        }
    }
    
    @Transactional(readOnly = true)
    public InvoiceDTO getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found: " + id));
        return invoiceMapper.toDTO(invoice);
    }
    
    @Transactional(readOnly = true)
    public List<InvoiceDTO> getInvoicesByCustomer(Long customerId) {
        return invoiceRepository.findByCustomerId(customerId).stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public InvoiceDTO markInvoiceAsPaid(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found: " + invoiceId));
        
        if (invoice.getStatus() == Invoice.InvoiceStatus.PAID) {
            log.warn("Invoice {} is already paid", invoiceId);
            return invoiceMapper.toDTO(invoice);
        }
        
        invoice.setStatus(Invoice.InvoiceStatus.PAID);
        invoice = invoiceRepository.save(invoice);
        
        log.info("Marked invoice {} as paid", invoiceId);
        return invoiceMapper.toDTO(invoice);
    }
    
    public void triggerReconciliation(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found: " + invoiceId));
        
        // Reconciliation logic - check payments, credits, etc.
        log.info("Triggering reconciliation for invoice: {}", invoiceId);
        // Implementation would check payment status, apply credits, etc.
    }
    
    @Transactional(readOnly = true)
    public List<InvoiceDTO> getAllInvoices() {
        return invoiceRepository.findAll().stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }
}
