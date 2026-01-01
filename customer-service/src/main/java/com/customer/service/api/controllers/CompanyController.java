package com.customer.service.api.controllers;

import com.customer.service.api.dto.CompanyRequest;
import com.customer.service.api.dto.CompanyResponse;
import com.customer.service.application.services.CompanyService;
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
@RequestMapping("/api/companies")
@RequiredArgsConstructor
@Tag(name = "Company", description = "Company management API")
public class CompanyController {
    
    private final CompanyService companyService;
    
    @PostMapping
    @Operation(summary = "Create a new company", description = "Creates a new company with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Company created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<CompanyResponse> createCompany(@Valid @RequestBody CompanyRequest request) {
        log.info("Creating company: {}", request);
        CompanyResponse response = companyService.createCompany(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    @Operation(summary = "Get all companies", description = "Retrieves a list of all companies")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Companies retrieved successfully")
    })
    public ResponseEntity<List<CompanyResponse>> getAllCompanies() {
        log.info("Fetching all companies");
        List<CompanyResponse> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get company by ID", description = "Retrieves a company by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Company found"),
        @ApiResponse(responseCode = "404", description = "Company not found")
    })
    public ResponseEntity<CompanyResponse> getCompanyById(
            @Parameter(description = "Company ID", required = true) @PathVariable UUID id) {
        log.info("Fetching company with id: {}", id);
        CompanyResponse response = companyService.getCompanyById(id);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update company", description = "Updates an existing company")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Company updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Company not found")
    })
    public ResponseEntity<CompanyResponse> updateCompany(
            @Parameter(description = "Company ID", required = true) @PathVariable UUID id,
            @Valid @RequestBody CompanyRequest request) {
        log.info("Updating company with id: {}", id);
        CompanyResponse response = companyService.updateCompany(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete company", description = "Deletes a company by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Company deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Company not found")
    })
    public ResponseEntity<Void> deleteCompany(
            @Parameter(description = "Company ID", required = true) @PathVariable UUID id) {
        log.info("Deleting company with id: {}", id);
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}

