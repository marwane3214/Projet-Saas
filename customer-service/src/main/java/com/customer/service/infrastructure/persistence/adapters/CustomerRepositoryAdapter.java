package com.customer.service.infrastructure.persistence.adapters;

import com.customer.service.domain.entities.Customer;
import com.customer.service.domain.repositories.CustomerRepository;
import com.customer.service.infrastructure.persistence.entities.CustomerEntity;
import com.customer.service.infrastructure.persistence.mappers.CustomerEntityMapper;
import com.customer.service.infrastructure.persistence.repositories.JpaCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepository {
    
    private final JpaCustomerRepository jpaRepository;
    private final CustomerEntityMapper mapper;
    
    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = mapper.toEntity(customer);
        CustomerEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<Customer> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }
    
    @Override
    public List<Customer> findAll() {
        return jpaRepository.findAll().stream()
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
    
    @Override
    public List<Customer> findByCompanyId(UUID companyId) {
        return jpaRepository.findByCompanyId(companyId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}

