package com.customer.service.domain.repositories;

import com.customer.service.domain.entities.BillingAddress;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BillingAddressRepository {
    BillingAddress save(BillingAddress billingAddress);
    Optional<BillingAddress> findById(UUID id);
    List<BillingAddress> findAll();
    void deleteById(UUID id);
    boolean existsById(UUID id);
}

