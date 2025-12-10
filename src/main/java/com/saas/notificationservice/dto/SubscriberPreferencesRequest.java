package com.saas.notificationservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriberPreferencesRequest {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Email enabled is required")
    private Boolean emailEnabled;

    @NotNull(message = "SMS enabled is required")
    private Boolean smsEnabled;

    @NotNull(message = "Push enabled is required")
    private Boolean pushEnabled;

    @Size(max = 10, message = "Language must not exceed 10 characters")
    private String language;
}

