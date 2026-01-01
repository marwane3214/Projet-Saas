package com.customer.service.integration;

import com.customer.service.api.dto.CompanyRequest;
import com.customer.service.api.dto.CustomerRequest;
import com.customer.service.api.dto.CustomerResponse;
import com.customer.service.application.services.CompanyService;
import com.customer.service.application.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CustomerIntegrationTest {
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private CompanyService companyService;
    
    private UUID companyId;
    
    @BeforeEach
    void setUp() {
        // Create a company for testing
        CompanyRequest companyRequest = CompanyRequest.builder()
                .legalName("Test Company")
                .vatNumber("FR" + UUID.randomUUID().toString().substring(0, 11).replace("-", ""))
                .country("France")
                .build();
        
        companyId = companyService.createCompany(companyRequest).getId();
    }
    
    @Test
    void createAndRetrieveCustomer_Success() {
        // Given
        CustomerRequest request = CustomerRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("+33123456789")
                .companyId(companyId)
                .build();
        
        // When
        CustomerResponse created = customerService.createCustomer(request);
        
        // Then
        assertNotNull(created.getId());
        assertEquals("John", created.getFirstName());
        assertEquals("Doe", created.getLastName());
        assertEquals("john.doe@example.com", created.getEmail());
        
        // Retrieve
        CustomerResponse retrieved = customerService.getCustomerById(created.getId());
        assertNotNull(retrieved);
        assertEquals(created.getId(), retrieved.getId());
        assertEquals("John", retrieved.getFirstName());
    }
    
    @Test
    void updateCustomer_Success() {
        // Given
        CustomerRequest createRequest = CustomerRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("+33123456789")
                .companyId(companyId)
                .build();
        
        CustomerResponse created = customerService.createCustomer(createRequest);
        
        // When
        CustomerRequest updateRequest = CustomerRequest.builder()
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .phone("+33987654321")
                .companyId(companyId)
                .build();
        
        CustomerResponse updated = customerService.updateCustomer(created.getId(), updateRequest);
        
        // Then
        assertEquals("Jane", updated.getFirstName());
        assertEquals("Smith", updated.getLastName());
        assertEquals("jane.smith@example.com", updated.getEmail());
    }
    
    @Test
    void deleteCustomer_Success() {
        // Given
        CustomerRequest request = CustomerRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("+33123456789")
                .companyId(companyId)
                .build();
        
        CustomerResponse created = customerService.createCustomer(request);
        UUID customerId = created.getId();
        
        // When
        customerService.deleteCustomer(customerId);
        
        // Then
        assertThrows(Exception.class, () -> {
            customerService.getCustomerById(customerId);
        });
    }
}

