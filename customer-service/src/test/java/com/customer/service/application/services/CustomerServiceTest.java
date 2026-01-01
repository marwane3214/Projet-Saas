package com.customer.service.application.services;

import com.customer.service.api.dto.CustomerRequest;
import com.customer.service.api.dto.CustomerResponse;
import com.customer.service.api.exceptions.ResourceNotFoundException;
import com.customer.service.application.mappers.CustomerMapper;
import com.customer.service.domain.entities.Customer;
import com.customer.service.domain.repositories.CompanyRepository;
import com.customer.service.domain.repositories.CustomerRepository;
import com.customer.service.domain.value-objects.Email;
import com.customer.service.domain.value-objects.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    
    @Mock
    private CustomerRepository customerRepository;
    
    @Mock
    private CompanyRepository companyRepository;
    
    @Mock
    private CustomerMapper customerMapper;
    
    @InjectMocks
    private CustomerService customerService;
    
    private UUID customerId;
    private UUID companyId;
    private CustomerRequest customerRequest;
    private Customer customer;
    private CustomerResponse customerResponse;
    
    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        companyId = UUID.randomUUID();
        
        customerRequest = CustomerRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("+33123456789")
                .companyId(companyId)
                .build();
        
        customer = Customer.builder()
                .id(customerId)
                .firstName("John")
                .lastName("Doe")
                .email(new Email("john.doe@example.com"))
                .phone(new PhoneNumber("+33123456789"))
                .companyId(companyId)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        customerResponse = CustomerResponse.builder()
                .id(customerId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("+33123456789")
                .companyId(companyId)
                .build();
    }
    
    @Test
    void createCustomer_Success() {
        // Given
        when(companyRepository.existsById(companyId)).thenReturn(true);
        when(customerMapper.toDomain(customerRequest)).thenReturn(customer);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(customerMapper.toResponse(customer)).thenReturn(customerResponse);
        
        // When
        CustomerResponse result = customerService.createCustomer(customerRequest);
        
        // Then
        assertNotNull(result);
        assertEquals(customerId, result.getId());
        verify(companyRepository).existsById(companyId);
        verify(customerRepository).save(any(Customer.class));
    }
    
    @Test
    void createCustomer_CompanyNotFound_ThrowsException() {
        // Given
        when(companyRepository.existsById(companyId)).thenReturn(false);
        
        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            customerService.createCustomer(customerRequest);
        });
        
        verify(customerRepository, never()).save(any());
    }
    
    @Test
    void getCustomerById_Success() {
        // Given
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerMapper.toResponse(customer)).thenReturn(customerResponse);
        
        // When
        CustomerResponse result = customerService.getCustomerById(customerId);
        
        // Then
        assertNotNull(result);
        assertEquals(customerId, result.getId());
        verify(customerRepository).findById(customerId);
    }
    
    @Test
    void getCustomerById_NotFound_ThrowsException() {
        // Given
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            customerService.getCustomerById(customerId);
        });
    }
    
    @Test
    void getAllCustomers_Success() {
        // Given
        List<Customer> customers = List.of(customer);
        when(customerRepository.findAll()).thenReturn(customers);
        when(customerMapper.toResponse(customer)).thenReturn(customerResponse);
        
        // When
        List<CustomerResponse> result = customerService.getAllCustomers();
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(customerRepository).findAll();
    }
    
    @Test
    void updateCustomer_Success() {
        // Given
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(companyRepository.existsById(companyId)).thenReturn(true);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(customerMapper.toResponse(customer)).thenReturn(customerResponse);
        
        // When
        CustomerResponse result = customerService.updateCustomer(customerId, customerRequest);
        
        // Then
        assertNotNull(result);
        verify(customerRepository).findById(customerId);
        verify(customerRepository).save(any(Customer.class));
    }
    
    @Test
    void deleteCustomer_Success() {
        // Given
        when(customerRepository.existsById(customerId)).thenReturn(true);
        doNothing().when(customerRepository).deleteById(customerId);
        
        // When
        customerService.deleteCustomer(customerId);
        
        // Then
        verify(customerRepository).existsById(customerId);
        verify(customerRepository).deleteById(customerId);
    }
    
    @Test
    void deleteCustomer_NotFound_ThrowsException() {
        // Given
        when(customerRepository.existsById(customerId)).thenReturn(false);
        
        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            customerService.deleteCustomer(customerId);
        });
        
        verify(customerRepository, never()).deleteById(any());
    }
}

