package com.saas.notificationservice.dto;

import com.saas.notificationservice.enums.NotificationChannel;
import com.saas.notificationservice.enums.NotificationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Template ID is required")
    private Long templateId;

    @NotNull(message = "Channel is required")
    private NotificationChannel channel;

    @NotNull(message = "Status is required")
    private NotificationStatus status;

    private LocalDateTime scheduledAt;
    private String payload;
}

