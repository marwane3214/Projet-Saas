package com.saas.notificationservice.repository;

import com.saas.notificationservice.entity.SubscriberPreferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriberPreferencesRepository extends JpaRepository<SubscriberPreferences, Long> {
    Optional<SubscriberPreferences> findByCustomerId(Long customerId);
}

