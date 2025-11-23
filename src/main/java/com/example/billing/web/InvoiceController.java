package com.example.billing.web;

import com.example.billing.application.dto.CreateInvoiceRequest;
import com.example.billing.application.dto.InvoiceDTO;
import com.example.billing.service.InvoiceService;
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
@RequestMapping("/invoices")
@RequiredArgsConstructor
@Tag(name = "Invoices", description = "Invoice management API")
public class InvoiceController {
    
    private final InvoiceService invoiceService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create invoice", description = "Create a new invoice manually or automatically")
    public ResponseEntity<InvoiceDTO> createInvoice(@Valid @RequestBody CreateInvoiceRequest request) {
        InvoiceDTO invoice = invoiceService.createInvoice(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(invoice);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get invoice by ID", description = "Retrieve invoice details by ID")
    public ResponseEntity<InvoiceDTO> getInvoice(
            @Parameter(description = "Invoice ID") @PathVariable Long id) {
        InvoiceDTO invoice = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(invoice);
    }
    
    @GetMapping("/customer/{customerId}")
    @Operation(summary = "List invoices by customer", description = "Get all invoices for a specific customer")
    public ResponseEntity<List<InvoiceDTO>> getInvoicesByCustomer(
            @Parameter(description = "Customer ID") @PathVariable Long customerId) {
        List<InvoiceDTO> invoices = invoiceService.getInvoicesByCustomer(customerId);
        return ResponseEntity.ok(invoices);
    }
    
    @PostMapping("/{id}/pay")
    @Operation(summary = "Mark invoice as paid", description = "Mark an invoice as paid")
    public ResponseEntity<InvoiceDTO> markInvoiceAsPaid(
            @Parameter(description = "Invoice ID") @PathVariable Long id) {
        InvoiceDTO invoice = invoiceService.markInvoiceAsPaid(id);
        return ResponseEntity.ok(invoice);
    }
    
    @PostMapping("/{id}/reconcile")
    @Operation(summary = "Trigger reconciliation", description = "Trigger reconciliation for an invoice")
    public ResponseEntity<Void> triggerReconciliation(
            @Parameter(description = "Invoice ID") @PathVariable Long id) {
        invoiceService.triggerReconciliation(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping
    @Operation(summary = "List all invoices", description = "Get all invoices")
    public ResponseEntity<List<InvoiceDTO>> getAllInvoices() {
        List<InvoiceDTO> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }
}
