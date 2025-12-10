package com.saas.notificationservice.controller;

import com.saas.notificationservice.dto.NotificationTemplateRequest;
import com.saas.notificationservice.dto.NotificationTemplateResponse;
import com.saas.notificationservice.service.NotificationTemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
@RequiredArgsConstructor
public class NotificationTemplateController {

    private final NotificationTemplateService service;

    @PostMapping
    public ResponseEntity<NotificationTemplateResponse> create(@Valid @RequestBody NotificationTemplateRequest request) {
        NotificationTemplateResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<NotificationTemplateResponse>> getAll() {
        List<NotificationTemplateResponse> responses = service.getAll();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationTemplateResponse> getById(@PathVariable Long id) {
        NotificationTemplateResponse response = service.getById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationTemplateResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody NotificationTemplateRequest request) {
        NotificationTemplateResponse response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

