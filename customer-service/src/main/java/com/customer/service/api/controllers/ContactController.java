package com.customer.service.api.controllers;

import com.customer.service.api.dto.ContactRequest;
import com.customer.service.api.dto.ContactResponse;
import com.customer.service.application.services.ContactService;
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
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
@Tag(name = "Contact", description = "Contact management API")
public class ContactController {
    
    private final ContactService contactService;
    
    @PostMapping
    @Operation(summary = "Create a new contact", description = "Creates a new contact for a customer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Contact created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    public ResponseEntity<ContactResponse> createContact(@Valid @RequestBody ContactRequest request) {
        log.info("Creating contact: {}", request);
        ContactResponse response = contactService.createContact(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    @Operation(summary = "Get all contacts", description = "Retrieves a list of all contacts")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contacts retrieved successfully")
    })
    public ResponseEntity<List<ContactResponse>> getAllContacts() {
        log.info("Fetching all contacts");
        List<ContactResponse> contacts = contactService.getAllContacts();
        return ResponseEntity.ok(contacts);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get contact by ID", description = "Retrieves a contact by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contact found"),
        @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    public ResponseEntity<ContactResponse> getContactById(
            @Parameter(description = "Contact ID", required = true) @PathVariable UUID id) {
        log.info("Fetching contact with id: {}", id);
        ContactResponse response = contactService.getContactById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/customers/{customerId}")
    @Operation(summary = "Get contacts by customer ID", description = "Retrieves all contacts for a specific customer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contacts retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    public ResponseEntity<List<ContactResponse>> getContactsByCustomerId(
            @Parameter(description = "Customer ID", required = true) @PathVariable UUID customerId) {
        log.info("Fetching contacts for customer with id: {}", customerId);
        List<ContactResponse> contacts = contactService.getContactsByCustomerId(customerId);
        return ResponseEntity.ok(contacts);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update contact", description = "Updates an existing contact")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contact updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    public ResponseEntity<ContactResponse> updateContact(
            @Parameter(description = "Contact ID", required = true) @PathVariable UUID id,
            @Valid @RequestBody ContactRequest request) {
        log.info("Updating contact with id: {}", id);
        ContactResponse response = contactService.updateContact(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete contact", description = "Deletes a contact by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Contact deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    public ResponseEntity<Void> deleteContact(
            @Parameter(description = "Contact ID", required = true) @PathVariable UUID id) {
        log.info("Deleting contact with id: {}", id);
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}

