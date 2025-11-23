package com.example.billing.repository;

import com.example.billing.domain.Renewal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RenewalRepository extends JpaRepository<Renewal, Long> {
    List<Renewal> findBySubscriptionId(Long subscriptionId);
    
    List<Renewal> findByStatus(Renewal.RenewalStatus status);
    
    @Query("SELECT r FROM Renewal r WHERE r.renewalDate <= :date AND r.status = :status")
    List<Renewal> findDueRenewals(@Param("date") LocalDate date, 
                                   @Param("status") Renewal.RenewalStatus status);
    
    @Query("SELECT r FROM Renewal r WHERE r.status = :status AND (r.retryCount IS NULL OR r.retryCount < :maxRetries)")
    List<Renewal> findFailedRenewalsForRetry(@Param("status") Renewal.RenewalStatus status, 
                                             @Param("maxRetries") Integer maxRetries);
}
