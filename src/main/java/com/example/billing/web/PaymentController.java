package com.example.billing.web;

import com.example.billing.application.dto.PaymentDTO;
import com.example.billing.application.dto.RegisterPaymentRequest;
import com.example.billing.service.PaymentService;
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
@RequestMapping("/payments")
@RequiredArgsConstructor
@Tag(name = "Payments", description = "Payment management API")
public class PaymentController {
    
    private final PaymentService paymentService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register payment", description = "Register a new payment for an invoice")
    public ResponseEntity<PaymentDTO> registerPayment(@Valid @RequestBody RegisterPaymentRequest request) {
        PaymentDTO payment = paymentService.registerPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get payment by ID", description = "Retrieve payment details by ID")
    public ResponseEntity<PaymentDTO> getPayment(
            @Parameter(description = "Payment ID") @PathVariable Long id) {
        PaymentDTO payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }
    
    @GetMapping("/invoice/{invoiceId}")
    @Operation(summary = "Get payments by invoice", description = "Get all payments for a specific invoice")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByInvoice(
            @Parameter(description = "Invoice ID") @PathVariable Long invoiceId) {
        List<PaymentDTO> payments = paymentService.getPaymentsByInvoice(invoiceId);
        return ResponseEntity.ok(payments);
    }
}
