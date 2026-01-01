package com.customer.service.domain.repositories;

import com.customer.service.domain.entities.Company;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository {
    Company save(Company company);
    Optional<Company> findById(UUID id);
    List<Company> findAll();
    void deleteById(UUID id);
    boolean existsById(UUID id);
    Optional<Company> findByVatNumber(String vatNumber);
}

