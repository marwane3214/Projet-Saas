package com.example.billing.repository;

import com.example.billing.domain.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByNumber(String number);
    
    List<Invoice> findByCustomerId(Long customerId);
    
    List<Invoice> findBySubscriptionId(Long subscriptionId);
    
    List<Invoice> findByStatus(Invoice.InvoiceStatus status);
    
    @Query("SELECT i FROM Invoice i WHERE i.customerId = :customerId AND i.status = :status")
    List<Invoice> findByCustomerIdAndStatus(@Param("customerId") Long customerId, 
                                            @Param("status") Invoice.InvoiceStatus status);
}
