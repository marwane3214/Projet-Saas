package com.customer.service.application.services;

import com.customer.service.api.dto.CompanyRequest;
import com.customer.service.api.dto.CompanyResponse;
import com.customer.service.api.exceptions.BusinessException;
import com.customer.service.api.exceptions.ResourceNotFoundException;
import com.customer.service.application.mappers.CompanyMapper;
import com.customer.service.domain.entities.Company;
import com.customer.service.domain.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CompanyService {
    
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    
    public CompanyResponse createCompany(CompanyRequest request) {
        log.debug("Creating company with VAT: {}", request.getVatNumber());
        
        // Check if VAT number already exists
        companyRepository.findByVatNumber(request.getVatNumber().toUpperCase())
                .ifPresent(company -> {
                    throw new BusinessException("Company with VAT number " + request.getVatNumber() + " already exists");
                });
        
        Company company = companyMapper.toDomain(request);
        company.setId(UUID.randomUUID());
        company.setCreatedAt(LocalDateTime.now());
        
        Company saved = companyRepository.save(company);
        log.info("Company created with id: {}", saved.getId());
        
        return companyMapper.toResponse(saved);
    }
    
    @Transactional(readOnly = true)
    public List<CompanyResponse> getAllCompanies() {
        log.debug("Fetching all companies");
        return companyRepository.findAll().stream()
                .map(companyMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public CompanyResponse getCompanyById(UUID id) {
        log.debug("Fetching company with id: {}", id);
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company", id));
        return companyMapper.toResponse(company);
    }
    
    public CompanyResponse updateCompany(UUID id, CompanyRequest request) {
        log.debug("Updating company with id: {}", id);
        
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company", id));
        
        // Check if VAT number is being changed and if it's already taken
        if (request.getVatNumber() != null && !request.getVatNumber().equals(company.getVatNumber().getValue())) {
            companyRepository.findByVatNumber(request.getVatNumber().toUpperCase())
                    .ifPresent(existingCompany -> {
                        if (!existingCompany.getId().equals(id)) {
                            throw new BusinessException("Company with VAT number " + request.getVatNumber() + " already exists");
                        }
                    });
        }
        
        companyMapper.updateDomain(company, request);
        
        Company updated = companyRepository.save(company);
        log.info("Company updated with id: {}", updated.getId());
        
        return companyMapper.toResponse(updated);
    }
    
    public void deleteCompany(UUID id) {
        log.debug("Deleting company with id: {}", id);
        
        if (!companyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Company", id);
        }
        
        companyRepository.deleteById(id);
        log.info("Company deleted with id: {}", id);
    }
}

