package com.customer.service.domain.repositories;

import com.customer.service.domain.entities.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer save(Customer customer);
    Optional<Customer> findById(UUID id);
    List<Customer> findAll();
    void deleteById(UUID id);
    boolean existsById(UUID id);
    List<Customer> findByCompanyId(UUID companyId);
}

