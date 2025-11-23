package com.customer.service.domain.repositories;

import com.customer.service.domain.entities.Contact;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContactRepository {
    Contact save(Contact contact);
    Optional<Contact> findById(UUID id);
    List<Contact> findAll();
    List<Contact> findByCustomerId(UUID customerId);
    void deleteById(UUID id);
    boolean existsById(UUID id);
}

