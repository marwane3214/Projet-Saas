package com.example.billing.web;

import com.example.billing.application.dto.CreditDTO;
import com.example.billing.application.dto.IssueCreditRequest;
import com.example.billing.service.CreditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credits")
@RequiredArgsConstructor
@Tag(name = "Credits", description = "Credit management API")
public class CreditController {
    
    private final CreditService creditService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Issue credit", description = "Issue a credit note to a customer")
    public ResponseEntity<CreditDTO> issueCredit(@Valid @RequestBody IssueCreditRequest request) {
        CreditDTO credit = creditService.issueCredit(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(credit);
    }
    
    @PostMapping("/{creditId}/apply/{invoiceId}")
    @Operation(summary = "Apply credit to invoice", description = "Apply a credit to a specific invoice")
    public ResponseEntity<CreditDTO> applyCreditToInvoice(
            @Parameter(description = "Credit ID") @PathVariable Long creditId,
            @Parameter(description = "Invoice ID") @PathVariable Long invoiceId) {
        CreditDTO credit = creditService.applyCreditToInvoice(creditId, invoiceId);
        return ResponseEntity.ok(credit);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get credit by ID", description = "Retrieve credit details by ID")
    public ResponseEntity<CreditDTO> getCredit(
            @Parameter(description = "Credit ID") @PathVariable Long id) {
        CreditDTO credit = creditService.getCreditById(id);
        return ResponseEntity.ok(credit);
    }
    
    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get credits by customer", description = "Get all credits for a specific customer")
    public ResponseEntity<List<CreditDTO>> getCreditsByCustomer(
            @Parameter(description = "Customer ID") @PathVariable Long customerId) {
        List<CreditDTO> credits = creditService.getCreditsByCustomer(customerId);
        return ResponseEntity.ok(credits);
    }
}
