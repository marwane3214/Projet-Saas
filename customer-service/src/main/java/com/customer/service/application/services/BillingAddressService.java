package com.customer.service.application.services;

import com.customer.service.api.dto.BillingAddressRequest;
import com.customer.service.api.dto.BillingAddressResponse;
import com.customer.service.api.exceptions.ResourceNotFoundException;
import com.customer.service.application.mappers.BillingAddressMapper;
import com.customer.service.domain.entities.BillingAddress;
import com.customer.service.domain.entities.Customer;
import com.customer.service.domain.repositories.BillingAddressRepository;
import com.customer.service.domain.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BillingAddressService extends ContactService {
    
    private final BillingAddressRepository billingAddressRepository;
    private final CustomerRepository customerRepository;
    private final BillingAddressMapper billingAddressMapper;
    
    public BillingAddressResponse createBillingAddress(BillingAddressRequest request) {
        log.debug("Creating billing address");
        
        BillingAddress billingAddress = billingAddressMapper.toDomain(request);
        billingAddress.setId(UUID.randomUUID());
        
        BillingAddress saved = billingAddressRepository.save(billingAddress);
        log.info("Billing address created with id: {}", saved.getId());
        
        return billingAddressMapper.toResponse(saved);
    }
    
    @Transactional(readOnly = true)
    public List<BillingAddressResponse> getAllBillingAddresses() {
        log.debug("Fetching all billing addresses");
        return billingAddressRepository.findAll().stream()
                .map(billingAddressMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public BillingAddressResponse getBillingAddressById(UUID id) {
        log.debug("Fetching billing address with id: {}", id);
        BillingAddress billingAddress = billingAddressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BillingAddress", id));
        return billingAddressMapper.toResponse(billingAddress);
    }
    
    public BillingAddressResponse updateBillingAddress(UUID id, BillingAddressRequest request) {
        log.debug("Updating billing address with id: {}", id);
        
        BillingAddress billingAddress = billingAddressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BillingAddress", id));
        
        billingAddressMapper.updateDomain(billingAddress, request);
        
        BillingAddress updated = billingAddressRepository.save(billingAddress);
        log.info("Billing address updated with id: {}", updated.getId());
        
        return billingAddressMapper.toResponse(updated);
    }
    
    public void deleteBillingAddress(UUID id) {
        log.debug("Deleting billing address with id: {}", id);
        
        if (!billingAddressRepository.existsById(id)) {
            throw new ResourceNotFoundException("BillingAddress", id);
        }
        
        billingAddressRepository.deleteById(id);
        log.info("Billing address deleted with id: {}", id);
    }
    
    public void attachToCustomer(UUID billingAddressId, UUID customerId) {
        log.debug("Attaching billing address {} to customer {}", billingAddressId, customerId);
        
        BillingAddress billingAddress = billingAddressRepository.findById(billingAddressId)
                .orElseThrow(() -> new ResourceNotFoundException("BillingAddress", billingAddressId));
        
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", customerId));
        
        customer.setBillingAddressId(billingAddressId);
        customerRepository.save(customer);
        
        log.info("Billing address {} attached to customer {}", billingAddressId, customerId);
    }
    
    public void detachFromCustomer(UUID billingAddressId) {
        log.debug("Detaching billing address {} from customer", billingAddressId);
        
        BillingAddress billingAddress = billingAddressRepository.findById(billingAddressId)
                .orElseThrow(() -> new ResourceNotFoundException("BillingAddress", billingAddressId));
        
        // Find customers with this billing address and detach
        List<Customer> customers = customerRepository.findAll().stream()
                .filter(c -> billingAddressId.equals(c.getBillingAddressId()))
                .toList();
        
        for (Customer customer : customers) {
            customer.setBillingAddressId(null);
            customerRepository.save(customer);
        }
        
        log.info("Billing address {} detached from {} customer(s)", billingAddressId, customers.size());
    }
}

