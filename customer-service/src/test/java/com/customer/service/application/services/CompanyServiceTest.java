package com.customer.service.application.services;

import com.customer.service.api.dto.CompanyRequest;
import com.customer.service.api.dto.CompanyResponse;
import com.customer.service.api.exceptions.BusinessException;
import com.customer.service.api.exceptions.ResourceNotFoundException;
import com.customer.service.application.mappers.CompanyMapper;
import com.customer.service.domain.entities.Company;
import com.customer.service.domain.repositories.CompanyRepository;
import com.customer.service.domain.value-objects.VatNumber;
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
class CompanyServiceTest {
    
    @Mock
    private CompanyRepository companyRepository;
    
    @Mock
    private CompanyMapper companyMapper;
    
    @InjectMocks
    private CompanyService companyService;
    
    private UUID companyId;
    private CompanyRequest companyRequest;
    private Company company;
    private CompanyResponse companyResponse;
    
    @BeforeEach
    void setUp() {
        companyId = UUID.randomUUID();
        
        companyRequest = CompanyRequest.builder()
                .legalName("Acme Corp")
                .vatNumber("FR12345678901")
                .country("France")
                .build();
        
        company = Company.builder()
                .id(companyId)
                .legalName("Acme Corp")
                .vatNumber(new VatNumber("FR12345678901"))
                .country("France")
                .createdAt(LocalDateTime.now())
                .build();
        
        companyResponse = CompanyResponse.builder()
                .id(companyId)
                .legalName("Acme Corp")
                .vatNumber("FR12345678901")
                .country("France")
                .build();
    }
    
    @Test
    void createCompany_Success() {
        // Given
        when(companyRepository.findByVatNumber("FR12345678901")).thenReturn(Optional.empty());
        when(companyMapper.toDomain(companyRequest)).thenReturn(company);
        when(companyRepository.save(any(Company.class))).thenReturn(company);
        when(companyMapper.toResponse(company)).thenReturn(companyResponse);
        
        // When
        CompanyResponse result = companyService.createCompany(companyRequest);
        
        // Then
        assertNotNull(result);
        assertEquals(companyId, result.getId());
        verify(companyRepository).findByVatNumber("FR12345678901");
        verify(companyRepository).save(any(Company.class));
    }
    
    @Test
    void createCompany_DuplicateVatNumber_ThrowsException() {
        // Given
        when(companyRepository.findByVatNumber("FR12345678901")).thenReturn(Optional.of(company));
        
        // When & Then
        assertThrows(BusinessException.class, () -> {
            companyService.createCompany(companyRequest);
        });
        
        verify(companyRepository, never()).save(any());
    }
    
    @Test
    void getCompanyById_Success() {
        // Given
        when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));
        when(companyMapper.toResponse(company)).thenReturn(companyResponse);
        
        // When
        CompanyResponse result = companyService.getCompanyById(companyId);
        
        // Then
        assertNotNull(result);
        assertEquals(companyId, result.getId());
    }
    
    @Test
    void getCompanyById_NotFound_ThrowsException() {
        // Given
        when(companyRepository.findById(companyId)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            companyService.getCompanyById(companyId);
        });
    }
}

