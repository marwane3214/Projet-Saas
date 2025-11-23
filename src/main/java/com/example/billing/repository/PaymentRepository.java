package com.example.billing.repository;

import com.example.billing.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByInvoiceId(Long invoiceId);
    
    List<Payment> findByStatus(Payment.PaymentStatus status);
    
    Optional<Payment> findByGatewayReference(String gatewayReference);
    
    @Query("SELECT p FROM Payment p WHERE p.invoiceId = :invoiceId AND p.status = :status")
    List<Payment> findByInvoiceIdAndStatus(@Param("invoiceId") Long invoiceId, 
                                           @Param("status") Payment.PaymentStatus status);
}
