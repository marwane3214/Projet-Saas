package com.customer.service.api.controllers;

import com.customer.service.api.dto.CustomerRequest;
import com.customer.service.api.dto.CustomerResponse;
import com.customer.service.application.services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private CustomerService customerService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void createCustomer_Success() throws Exception {
        // Given
        UUID customerId = UUID.randomUUID();
        UUID companyId = UUID.randomUUID();
        
        CustomerRequest request = CustomerRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("+33123456789")
                .companyId(companyId)
                .build();
        
        CustomerResponse response = CustomerResponse.builder()
                .id(customerId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("+33123456789")
                .companyId(companyId)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        when(customerService.createCustomer(any(CustomerRequest.class))).thenReturn(response);
        
        // When & Then
        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(customerId.toString()))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }
    
    @Test
    void getAllCustomers_Success() throws Exception {
        // Given
        UUID customerId = UUID.randomUUID();
        CustomerResponse response = CustomerResponse.builder()
                .id(customerId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("+33123456789")
                .build();
        
        when(customerService.getAllCustomers()).thenReturn(List.of(response));
        
        // When & Then
        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(customerId.toString()));
    }
    
    @Test
    void getCustomerById_Success() throws Exception {
        // Given
        UUID customerId = UUID.randomUUID();
        CustomerResponse response = CustomerResponse.builder()
                .id(customerId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("+33123456789")
                .build();
        
        when(customerService.getCustomerById(customerId)).thenReturn(response);
        
        // When & Then
        mockMvc.perform(get("/api/customers/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerId.toString()));
    }
}

