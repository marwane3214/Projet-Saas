package com.saas.notificationservice.dto;

import com.saas.notificationservice.enums.NotificationChannel;
import com.saas.notificationservice.enums.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {

    private Long id;
    private Long customerId;
    private Long templateId;
    private NotificationChannel channel;
    private NotificationStatus status;
    private LocalDateTime scheduledAt;
    private LocalDateTime sentAt;
    private String payload;
    private LocalDateTime createdAt;
}

