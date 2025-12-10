package com.saas.notificationservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RootController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getApiInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("service", "Notification Service");
        info.put("version", "1.0.0");
        info.put("description", "Microservice for notification management in SaaS subscription system");
        info.put("endpoints", Map.of(
                "templates", "/api/templates",
                "notifications", "/api/notifications",
                "channels", "/api/channels",
                "preferences", "/api/preferences",
                "health", "/actuator/health"
        ));
        return ResponseEntity.ok(info);
    }
}

