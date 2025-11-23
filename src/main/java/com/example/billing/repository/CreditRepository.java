package com.example.billing.repository;

import com.example.billing.domain.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Long> {
    List<Credit> findByCustomerId(Long customerId);
    
    List<Credit> findByInvoiceId(Long invoiceId);
    
    @Query("SELECT c FROM Credit c WHERE c.customerId = :customerId AND c.applied = false")
    List<Credit> findUnappliedCreditsByCustomerId(@Param("customerId") Long customerId);
}
