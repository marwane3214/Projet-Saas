package com.example.billing.service;

import com.example.billing.application.dto.CreditDTO;
import com.example.billing.application.dto.IssueCreditRequest;
import com.example.billing.application.mapper.CreditMapper;
import com.example.billing.domain.Credit;
import com.example.billing.domain.Invoice;
import com.example.billing.repository.CreditRepository;
import com.example.billing.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CreditService {
    
    private final CreditRepository creditRepository;
    private final InvoiceRepository invoiceRepository;
    private final CreditMapper creditMapper;
    
    public CreditDTO issueCredit(IssueCreditRequest request) {
        Credit credit = Credit.builder()
                .customerId(request.getCustomerId())
                .invoiceId(request.getInvoiceId())
                .amount(request.getAmount())
                .reason(request.getReason())
                .createdAt(LocalDateTime.now())
                .applied(false)
                .build();
        
        credit = creditRepository.save(credit);
        log.info("Issued credit {} for customer {}", credit.getId(), request.getCustomerId());
        
        return creditMapper.toDTO(credit);
    }
    
    public CreditDTO applyCreditToInvoice(Long creditId, Long invoiceId) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new IllegalArgumentException("Credit not found: " + creditId));
        
        if (credit.getApplied()) {
            throw new IllegalStateException("Credit " + creditId + " has already been applied");
        }
        
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found: " + invoiceId));
        
        // Validate customer matches
        if (!credit.getCustomerId().equals(invoice.getCustomerId())) {
            throw new IllegalStateException("Credit customer does not match invoice customer");
        }
        
        // Apply credit to invoice
        BigDecimal newAmount = invoice.getAmount().subtract(credit.getAmount());
        if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
            newAmount = BigDecimal.ZERO;
        }
        invoice.setAmount(newAmount);
        
        // If fully credited, mark as paid
        if (newAmount.compareTo(BigDecimal.ZERO) == 0) {
            invoice.setStatus(Invoice.InvoiceStatus.PAID);
        }
        
        invoiceRepository.save(invoice);
        
        credit.setApplied(true);
        credit.setInvoiceId(invoiceId);
        credit = creditRepository.save(credit);
        
        log.info("Applied credit {} to invoice {}", creditId, invoiceId);
        
        return creditMapper.toDTO(credit);
    }
    
    @Transactional(readOnly = true)
    public CreditDTO getCreditById(Long id) {
        Credit credit = creditRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Credit not found: " + id));
        return creditMapper.toDTO(credit);
    }
    
    @Transactional(readOnly = true)
    public List<CreditDTO> getCreditsByCustomer(Long customerId) {
        return creditRepository.findByCustomerId(customerId).stream()
                .map(creditMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<CreditDTO> getUnappliedCreditsByCustomer(Long customerId) {
        return creditRepository.findUnappliedCreditsByCustomerId(customerId).stream()
                .map(creditMapper::toDTO)
                .collect(Collectors.toList());
    }
}
