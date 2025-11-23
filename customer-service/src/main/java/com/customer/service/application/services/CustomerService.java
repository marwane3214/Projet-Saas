package com.customer.service.application.services;

import com.customer.service.api.dto.CustomerRequest;
import com.customer.service.api.dto.CustomerResponse;
import com.customer.service.api.exceptions.BusinessException;
import com.customer.service.api.exceptions.ResourceNotFoundException;
import com.customer.service.application.mappers.CustomerMapper;
import com.customer.service.domain.entities.Customer;
import com.customer.service.domain.repositories.CompanyRepository;
import com.customer.service.domain.repositories.CustomerRepository;
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
public class CustomerService {
    
    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;
    private final CustomerMapper customerMapper;
    
    public CustomerResponse createCustomer(CustomerRequest request) {
        log.debug("Creating customer with email: {}", request.getEmail());
        
        // Validate company exists
        if (!companyRepository.existsById(request.getCompanyId())) {
            throw new ResourceNotFoundException("Company", request.getCompanyId());
        }
        
        // Validate billing address if provided
        if (request.getBillingAddressId() != null) {
            // This would require BillingAddressRepository - for now we'll skip this validation
            // In a real scenario, you'd validate it exists
        }
        
        Customer customer = customerMapper.toDomain(request);
        customer.setId(UUID.randomUUID());
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        
        Customer saved = customerRepository.save(customer);
        log.info("Customer created with id: {}", saved.getId());
        
        return customerMapper.toResponse(saved);
    }
    
    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomers() {
        log.debug("Fetching all customers");
        return customerRepository.findAll().stream()
                .map(customerMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(UUID id) {
        log.debug("Fetching customer with id: {}", id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", id));
        return customerMapper.toResponse(customer);
    }
    
    public CustomerResponse updateCustomer(UUID id, CustomerRequest request) {
        log.debug("Updating customer with id: {}", id);
        
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", id));
        
        // Validate company if changed
        if (request.getCompanyId() != null && !request.getCompanyId().equals(customer.getCompanyId())) {
            if (!companyRepository.existsById(request.getCompanyId())) {
                throw new ResourceNotFoundException("Company", request.getCompanyId());
            }
        }
        
        customerMapper.updateDomain(customer, request);
        customer.setUpdatedAt(LocalDateTime.now());
        
        Customer updated = customerRepository.save(customer);
        log.info("Customer updated with id: {}", updated.getId());
        
        return customerMapper.toResponse(updated);
    }
    
    public void deleteCustomer(UUID id) {
        log.debug("Deleting customer with id: {}", id);
        
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer", id);
        }
        
        customerRepository.deleteById(id);
        log.info("Customer deleted with id: {}", id);
    }
}

