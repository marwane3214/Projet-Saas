package com.example.billing.web;

import com.example.billing.application.dto.CreateInvoiceRequest;
import com.example.billing.application.dto.InvoiceDTO;
import com.example.billing.service.InvoiceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InvoiceController.class)
class InvoiceControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private InvoiceService invoiceService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void testCreateInvoice() throws Exception {
        // Given
        CreateInvoiceRequest request = CreateInvoiceRequest.builder()
                .customerId(1L)
                .subscriptionId(1L)
                .amount(new BigDecimal("100.00"))
                .currency("USD")
                .dueDate(LocalDate.now().plusDays(30))
                .build();
        
        InvoiceDTO response = InvoiceDTO.builder()
                .id(1L)
                .customerId(1L)
                .subscriptionId(1L)
                .amount(new BigDecimal("100.00"))
                .build();
        
        when(invoiceService.createInvoice(any(CreateInvoiceRequest.class))).thenReturn(response);
        
        // When & Then
        mockMvc.perform(post("/invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerId").value(1L));
    }
}

