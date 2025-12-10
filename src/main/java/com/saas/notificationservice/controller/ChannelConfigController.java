package com.saas.notificationservice.controller;

import com.saas.notificationservice.dto.ChannelConfigRequest;
import com.saas.notificationservice.dto.ChannelConfigResponse;
import com.saas.notificationservice.service.ChannelConfigService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
public class ChannelConfigController {

    private final ChannelConfigService service;

    @PostMapping
    public ResponseEntity<ChannelConfigResponse> create(@Valid @RequestBody ChannelConfigRequest request) {
        ChannelConfigResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ChannelConfigResponse>> getAll() {
        List<ChannelConfigResponse> responses = service.getAll();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChannelConfigResponse> getById(@PathVariable Long id) {
        ChannelConfigResponse response = service.getById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChannelConfigResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ChannelConfigRequest request) {
        ChannelConfigResponse response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

