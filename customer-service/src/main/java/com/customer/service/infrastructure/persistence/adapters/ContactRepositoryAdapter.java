package com.customer.service.infrastructure.persistence.adapters;

import com.customer.service.domain.entities.Contact;
import com.customer.service.domain.repositories.ContactRepository;
import com.customer.service.infrastructure.persistence.entities.ContactEntity;
import com.customer.service.infrastructure.persistence.mappers.ContactEntityMapper;
import com.customer.service.infrastructure.persistence.repositories.JpaContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ContactRepositoryAdapter implements ContactRepository {
    
    private final JpaContactRepository jpaRepository;
    private final ContactEntityMapper mapper;
    
    @Override
    public Contact save(Contact contact) {
        ContactEntity entity = mapper.toEntity(contact);
        ContactEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<Contact> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }
    
    @Override
    public List<Contact> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Contact> findByCustomerId(UUID customerId) {
        return jpaRepository.findByCustomerId(customerId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }
}

