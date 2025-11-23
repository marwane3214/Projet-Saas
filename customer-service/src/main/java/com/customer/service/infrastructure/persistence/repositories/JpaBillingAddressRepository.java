package com.customer.service.infrastructure.persistence.repositories;

import com.customer.service.infrastructure.persistence.entities.BillingAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaBillingAddressRepository extends JpaRepository<BillingAddressEntity, UUID> {
}

