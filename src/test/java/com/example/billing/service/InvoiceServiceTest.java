package com.example.billing.service;

import com.example.billing.application.dto.CreateInvoiceRequest;
import com.example.billing.application.dto.InvoiceDTO;
import com.example.billing.domain.Invoice;
import com.example.billing.infrastructure.idempotency.IdempotencyService;
import com.example.billing.infrastructure.kafka.KafkaEventProducer;
import com.example.billing.repository.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {
    
    @Mock
    private InvoiceRepository invoiceRepository;
    
    @Mock
    private KafkaEventProducer kafkaEventProducer;
    
    @Mock
    private IdempotencyService idempotencyService;
    
    @InjectMocks
    private InvoiceService invoiceService;
    
    private CreateInvoiceRequest request;
    
    @BeforeEach
    void setUp() {
        request = CreateInvoiceRequest.builder()
                .customerId(1L)
                .subscriptionId(1L)
                .amount(new BigDecimal("100.00"))
                .currency("USD")
                .dueDate(LocalDate.now().plusDays(30))
                .build();
    }
    
    @Test
    void testCreateInvoice() {
        // Given
        Invoice savedInvoice = Invoice.builder()
                .id(1L)
                .customerId(1L)
                .subscriptionId(1L)
                .amount(new BigDecimal("100.00"))
                .currency("USD")
                .status(Invoice.InvoiceStatus.OPEN)
                .dueDate(LocalDate.now().plusDays(30))
                .build();
        
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(savedInvoice);
        when(idempotencyService.isDuplicate(anyString())).thenReturn(false);
        
        // When
        InvoiceDTO result = invoiceService.createInvoice(request);
        
        // Then
        assertNotNull(result);
        assertEquals(1L, result.getCustomerId());
        verify(invoiceRepository, times(1)).save(any(Invoice.class));
        verify(kafkaEventProducer, times(1)).publishInvoiceCreated(any());
    }
    
    @Test
    void testGetInvoiceById() {
        // Given
        Invoice invoice = Invoice.builder()
                .id(1L)
                .customerId(1L)
                .subscriptionId(1L)
                .amount(new BigDecimal("100.00"))
                .build();
        
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));
        
        // When
        InvoiceDTO result = invoiceService.getInvoiceById(1L);
        
        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
    
    @Test
    void testGetInvoiceByIdNotFound() {
        // Given
        when(invoiceRepository.findById(1L)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            invoiceService.getInvoiceById(1L);
        });
    }
}

