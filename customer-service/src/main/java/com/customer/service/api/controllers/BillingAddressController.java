package com.customer.service.api.controllers;

import com.customer.service.api.dto.BillingAddressRequest;
import com.customer.service.api.dto.BillingAddressResponse;
import com.customer.service.application.services.BillingAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/billing-addresses")
@RequiredArgsConstructor
@Tag(name = "Billing Address", description = "Billing address management API")
public class BillingAddressController {
    
    private final BillingAddressService billingAddressService;
    
    @PostMapping
    @Operation(summary = "Create a new billing address", description = "Creates a new billing address")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Billing address created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<BillingAddressResponse> createBillingAddress(@Valid @RequestBody BillingAddressRequest request) {
        log.info("Creating billing address: {}", request);
        BillingAddressResponse response = billingAddressService.createBillingAddress(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    @Operation(summary = "Get all billing addresses", description = "Retrieves a list of all billing addresses")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Billing addresses retrieved successfully")
    })
    public ResponseEntity<List<BillingAddressResponse>> getAllBillingAddresses() {
        log.info("Fetching all billing addresses");
        List<BillingAddressResponse> addresses = billingAddressService.getAllBillingAddresses();
        return ResponseEntity.ok(addresses);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get billing address by ID", description = "Retrieves a billing address by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Billing address found"),
        @ApiResponse(responseCode = "404", description = "Billing address not found")
    })
    public ResponseEntity<BillingAddressResponse> getBillingAddressById(
            @Parameter(description = "Billing Address ID", required = true) @PathVariable UUID id) {
        log.info("Fetching billing address with id: {}", id);
        BillingAddressResponse response = billingAddressService.getBillingAddressById(id);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update billing address", description = "Updates an existing billing address")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Billing address updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Billing address not found")
    })
    public ResponseEntity<BillingAddressResponse> updateBillingAddress(
            @Parameter(description = "Billing Address ID", required = true) @PathVariable UUID id,
            @Valid @RequestBody BillingAddressRequest request) {
        log.info("Updating billing address with id: {}", id);
        BillingAddressResponse response = billingAddressService.updateBillingAddress(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete billing address", description = "Deletes a billing address by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Billing address deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Billing address not found")
    })
    public ResponseEntity<Void> deleteBillingAddress(
            @Parameter(description = "Billing Address ID", required = true) @PathVariable UUID id) {
        log.info("Deleting billing address with id: {}", id);
        billingAddressService.deleteBillingAddress(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/attach/{customerId}")
    @Operation(summary = "Attach billing address to customer", description = "Attaches a billing address to a customer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Billing address attached successfully"),
        @ApiResponse(responseCode = "404", description = "Billing address or customer not found")
    })
    public ResponseEntity<Void> attachToCustomer(
            @Parameter(description = "Billing Address ID", required = true) @PathVariable UUID id,
            @Parameter(description = "Customer ID", required = true) @PathVariable UUID customerId) {
        log.info("Attaching billing address {} to customer {}", id, customerId);
        billingAddressService.attachToCustomer(id, customerId);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{id}/detach")
    @Operation(summary = "Detach billing address from customer", description = "Detaches a billing address from its customer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Billing address detached successfully"),
        @ApiResponse(responseCode = "404", description = "Billing address or customer not found")
    })
    public ResponseEntity<Void> detachFromCustomer(
            @Parameter(description = "Billing Address ID", required = true) @PathVariable UUID id) {
        log.info("Detaching billing address {} from customer", id);
        billingAddressService.detachFromCustomer(id);
        return ResponseEntity.ok().build();
    }
}

