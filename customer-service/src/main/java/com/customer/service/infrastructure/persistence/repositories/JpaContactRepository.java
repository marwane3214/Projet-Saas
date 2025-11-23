package com.customer.service.infrastructure.persistence.repositories;

import com.customer.service.infrastructure.persistence.entities.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaContactRepository extends JpaRepository<ContactEntity, UUID> {
    List<ContactEntity> findByCustomerId(UUID customerId);
}

