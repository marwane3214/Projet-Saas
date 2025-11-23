package com.customer.service.infrastructure.persistence.repositories;

import com.customer.service.infrastructure.persistence.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaCompanyRepository extends JpaRepository<CompanyEntity, UUID> {
    Optional<CompanyEntity> findByVatNumber(String vatNumber);
}

