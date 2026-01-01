package com.customer.service.infrastructure.persistence.repositories;

import com.customer.service.infrastructure.persistence.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, UUID> {
    List<CustomerEntity> findByCompanyId(UUID companyId);
}

