package com.customer.service.infrastructure.persistence.adapters;

import com.customer.service.domain.entities.Company;
import com.customer.service.domain.repositories.CompanyRepository;
import com.customer.service.infrastructure.persistence.entities.CompanyEntity;
import com.customer.service.infrastructure.persistence.mappers.CompanyEntityMapper;
import com.customer.service.infrastructure.persistence.repositories.JpaCompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompanyRepositoryAdapter implements CompanyRepository {
    
    private final JpaCompanyRepository jpaRepository;
    private final CompanyEntityMapper mapper;
    
    @Override
    public Company save(Company company) {
        CompanyEntity entity = mapper.toEntity(company);
        CompanyEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<Company> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }
    
    @Override
    public List<Company> findAll() {
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
    public Optional<Company> findByVatNumber(String vatNumber) {
        return jpaRepository.findByVatNumber(vatNumber)
                .map(mapper::toDomain);
    }
}

