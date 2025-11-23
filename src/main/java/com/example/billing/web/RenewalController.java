package com.example.billing.web;

import com.example.billing.application.dto.RenewalDTO;
import com.example.billing.application.dto.ScheduleRenewalRequest;
import com.example.billing.service.RenewalService;
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
@RequestMapping("/renewals")
@RequiredArgsConstructor
@Tag(name = "Renewals", description = "Renewal management API")
public class RenewalController {
    
    private final RenewalService renewalService;
    
    @PostMapping("/schedule")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Schedule renewal", description = "Schedule a renewal for a subscription")
    public ResponseEntity<RenewalDTO> scheduleRenewal(@Valid @RequestBody ScheduleRenewalRequest request) {
        RenewalDTO renewal = renewalService.scheduleRenewal(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(renewal);
    }
    
    @PostMapping("/process/{id}")
    @Operation(summary = "Process renewal", description = "Process a specific renewal")
    public ResponseEntity<RenewalDTO> processRenewal(
            @Parameter(description = "Renewal ID") @PathVariable Long id) {
        RenewalDTO renewal = renewalService.processRenewal(id);
        return ResponseEntity.ok(renewal);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get renewal by ID", description = "Retrieve renewal details by ID")
    public ResponseEntity<RenewalDTO> getRenewal(
            @Parameter(description = "Renewal ID") @PathVariable Long id) {
        RenewalDTO renewal = renewalService.getRenewalById(id);
        return ResponseEntity.ok(renewal);
    }
    
    @GetMapping("/subscription/{subscriptionId}")
    @Operation(summary = "Get renewals by subscription", description = "Get all renewals for a specific subscription")
    public ResponseEntity<List<RenewalDTO>> getRenewalsBySubscription(
            @Parameter(description = "Subscription ID") @PathVariable Long subscriptionId) {
        List<RenewalDTO> renewals = renewalService.getRenewalsBySubscription(subscriptionId);
        return ResponseEntity.ok(renewals);
    }
}
