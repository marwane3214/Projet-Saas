package com.customer.service.infrastructure.persistence.adapters;

import com.customer.service.domain.entities.Customer;
import com.customer.service.domain.value-objects.Email;
import com.customer.service.domain.value-objects.PhoneNumber;
import com.customer.service.infrastructure.persistence.entities.CustomerEntity;
import com.customer.service.infrastructure.persistence.mappers.CustomerEntityMapper;
import com.customer.service.infrastructure.persistence.repositories.JpaCustomerRepository;
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
class CustomerRepositoryAdapterTest {
    
    @Mock
    private JpaCustomerRepository jpaRepository;
    
    @Mock
    private CustomerEntityMapper mapper;
    
    @InjectMocks
    private CustomerRepositoryAdapter adapter;
    
    private UUID customerId;
    private CustomerEntity entity;
    private Customer domain;
    
    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        
        entity = CustomerEntity.builder()
                .id(customerId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("+33123456789")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        domain = Customer.builder()
                .id(customerId)
                .firstName("John")
                .lastName("Doe")
                .email(new Email("john.doe@example.com"))
                .phone(new PhoneNumber("+33123456789"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
    
    @Test
    void save_Success() {
        // Given
        when(mapper.toEntity(domain)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(domain);
        
        // When
        Customer result = adapter.save(domain);
        
        // Then
        assertNotNull(result);
        assertEquals(customerId, result.getId());
        verify(jpaRepository).save(entity);
    }
    
    @Test
    void findById_Success() {
        // Given
        when(jpaRepository.findById(customerId)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);
        
        // When
        Optional<Customer> result = adapter.findById(customerId);
        
        // Then
        assertTrue(result.isPresent());
        assertEquals(customerId, result.get().getId());
    }
    
    @Test
    void findById_NotFound() {
        // Given
        when(jpaRepository.findById(customerId)).thenReturn(Optional.empty());
        
        // When
        Optional<Customer> result = adapter.findById(customerId);
        
        // Then
        assertTrue(result.isEmpty());
    }
    
    @Test
    void existsById_Success() {
        // Given
        when(jpaRepository.existsById(customerId)).thenReturn(true);
        
        // When
        boolean result = adapter.existsById(customerId);
        
        // Then
        assertTrue(result);
        verify(jpaRepository).existsById(customerId);
    }
}

