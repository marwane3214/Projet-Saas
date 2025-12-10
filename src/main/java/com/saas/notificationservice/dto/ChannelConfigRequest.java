package com.saas.notificationservice.dto;

import com.saas.notificationservice.enums.NotificationChannel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannelConfigRequest {

    @NotNull(message = "Channel is required")
    private NotificationChannel channel;

    @NotBlank(message = "Config JSON is required")
    private String configJson;

    @NotNull(message = "IsActive is required")
    private Boolean isActive;
}

