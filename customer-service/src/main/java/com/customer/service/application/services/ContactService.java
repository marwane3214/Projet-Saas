package com.customer.service.application.services;

import com.customer.service.api.dto.ContactRequest;
import com.customer.service.api.dto.ContactResponse;
import com.customer.service.api.exceptions.ResourceNotFoundException;
import com.customer.service.application.mappers.ContactMapper;
import com.customer.service.domain.entities.Contact;
import com.customer.service.domain.repositories.ContactRepository;
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
public class ContactService {
    
    private final ContactRepository contactRepository;
    private final CustomerRepository customerRepository;
    private final ContactMapper contactMapper;
    
    public ContactResponse createContact(ContactRequest request) {
        log.debug("Creating contact for customer: {}", request.getCustomerId());
        
        // Validate customer exists
        if (!customerRepository.existsById(request.getCustomerId())) {
            throw new ResourceNotFoundException("Customer", request.getCustomerId());
        }
        
        Contact contact = contactMapper.toDomain(request);
        contact.setId(UUID.randomUUID());
        
        Contact saved = contactRepository.save(contact);
        log.info("Contact created with id: {}", saved.getId());
        
        return contactMapper.toResponse(saved);
    }
    
    @Transactional(readOnly = true)
    public List<ContactResponse> getAllContacts() {
        log.debug("Fetching all contacts");
        return contactRepository.findAll().stream()
                .map(contactMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public ContactResponse getContactById(UUID id) {
        log.debug("Fetching contact with id: {}", id);
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact", id));
        return contactMapper.toResponse(contact);
    }
    
    @Transactional(readOnly = true)
    public List<ContactResponse> getContactsByCustomerId(UUID customerId) {
        log.debug("Fetching contacts for customer: {}", customerId);
        
        // Validate customer exists
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer", customerId);
        }
        
        return contactRepository.findByCustomerId(customerId).stream()
                .map(contactMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    public ContactResponse updateContact(UUID id, ContactRequest request) {
        log.debug("Updating contact with id: {}", id);
        
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact", id));
        
        // Validate customer if changed
        if (request.getCustomerId() != null && !request.getCustomerId().equals(contact.getCustomerId())) {
            if (!customerRepository.existsById(request.getCustomerId())) {
                throw new ResourceNotFoundException("Customer", request.getCustomerId());
            }
        }
        
        contactMapper.updateDomain(contact, request);
        
        Contact updated = contactRepository.save(contact);
        log.info("Contact updated with id: {}", updated.getId());
        
        return contactMapper.toResponse(updated);
    }
    
    public void deleteContact(UUID id) {
        log.debug("Deleting contact with id: {}", id);
        
        if (!contactRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contact", id);
        }
        
        contactRepository.deleteById(id);
        log.info("Contact deleted with id: {}", id);
    }
}

