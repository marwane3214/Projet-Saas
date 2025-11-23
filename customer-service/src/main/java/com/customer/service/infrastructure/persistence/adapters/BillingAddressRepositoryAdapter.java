package com.customer.service.infrastructure.persistence.adapters;

import com.customer.service.domain.entities.BillingAddress;
import com.customer.service.domain.repositories.BillingAddressRepository;
import com.customer.service.infrastructure.persistence.entities.BillingAddressEntity;
import com.customer.service.infrastructure.persistence.mappers.BillingAddressEntityMapper;
import com.customer.service.infrastructure.persistence.repositories.JpaBillingAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BillingAddressRepositoryAdapter implements BillingAddressRepository {
    
    private final JpaBillingAddressRepository jpaRepository;
    private final BillingAddressEntityMapper mapper;
    
    @Override
    public BillingAddress save(BillingAddress billingAddress) {
        BillingAddressEntity entity = mapper.toEntity(billingAddress);
        BillingAddressEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<BillingAddress> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }
    
    @Override
    public List<BillingAddress> findAll() {
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
}

