package com.saas.notificationservice.dto;

import com.saas.notificationservice.enums.TemplateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationTemplateResponse {

    private Long id;
    private String name;
    private String subject;
    private String body;
    private TemplateType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

