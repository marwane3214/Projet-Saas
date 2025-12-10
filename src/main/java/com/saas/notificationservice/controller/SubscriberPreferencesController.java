package com.saas.notificationservice.controller;

import com.saas.notificationservice.dto.SubscriberPreferencesRequest;
import com.saas.notificationservice.dto.SubscriberPreferencesResponse;
import com.saas.notificationservice.service.SubscriberPreferencesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preferences")
@RequiredArgsConstructor
public class SubscriberPreferencesController {

    private final SubscriberPreferencesService service;

    @PostMapping
    public ResponseEntity<SubscriberPreferencesResponse> create(@Valid @RequestBody SubscriberPreferencesRequest request) {
        SubscriberPreferencesResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SubscriberPreferencesResponse>> getAll() {
        List<SubscriberPreferencesResponse> responses = service.getAll();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriberPreferencesResponse> getById(@PathVariable Long id) {
        SubscriberPreferencesResponse response = service.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<SubscriberPreferencesResponse> getByCustomerId(@PathVariable Long customerId) {
        SubscriberPreferencesResponse response = service.getByCustomerId(customerId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriberPreferencesResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody SubscriberPreferencesRequest request) {
        SubscriberPreferencesResponse response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

