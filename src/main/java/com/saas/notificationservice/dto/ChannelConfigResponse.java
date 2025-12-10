package com.saas.notificationservice.dto;

import com.saas.notificationservice.enums.NotificationChannel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChannelConfigResponse {

    private Long id;
    private NotificationChannel channel;
    private String configJson;
    private Boolean isActive;
    private LocalDateTime createdAt;
}

