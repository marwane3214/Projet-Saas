package com.saas.notificationservice.grpc;

import com.saas.notificationservice.dto.*;
import com.saas.notificationservice.enums.NotificationChannel;
import com.saas.notificationservice.enums.NotificationStatus;
import com.saas.notificationservice.enums.TemplateType;
import com.saas.notificationservice.proto.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ProtoConverter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // Template conversions
    public NotificationTemplateRequest toTemplateRequest(CreateTemplateRequest proto) {
        NotificationTemplateRequest request = new NotificationTemplateRequest();
        request.setName(proto.getName());
        request.setSubject(proto.getSubject());
        request.setBody(proto.getBody());
        request.setType(toTemplateType(proto.getType()));
        return request;
    }

    public TemplateResponse toTemplateProto(NotificationTemplateResponse dto) {
        return TemplateResponse.newBuilder()
                .setId(dto.getId())
                .setName(dto.getName())
                .setSubject(dto.getSubject())
                .setBody(dto.getBody())
                .setType(toTemplateTypeProto(dto.getType()))
                .setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt().format(FORMATTER) : "")
                .setUpdatedAt(dto.getUpdatedAt() != null ? dto.getUpdatedAt().format(FORMATTER) : "")
                .build();
    }

    public NotificationTemplateRequest toTemplateRequest(UpdateTemplateRequest proto) {
        NotificationTemplateRequest request = new NotificationTemplateRequest();
        request.setName(proto.getName());
        request.setSubject(proto.getSubject());
        request.setBody(proto.getBody());
        request.setType(toTemplateType(proto.getType()));
        return request;
    }

    // Notification conversions
    public NotificationRequest toNotificationRequest(CreateNotificationRequest proto) {
        NotificationRequest request = new NotificationRequest();
        request.setCustomerId(proto.getCustomerId());
        request.setTemplateId(proto.getTemplateId());
        request.setChannel(toNotificationChannel(proto.getChannel()));
        request.setStatus(toNotificationStatus(proto.getStatus()));
        if (!proto.getScheduledAt().isEmpty()) {
            try {
                request.setScheduledAt(LocalDateTime.parse(proto.getScheduledAt(), FORMATTER));
            } catch (Exception e) {
                // If parsing fails, leave scheduledAt as null
                // This allows the field to be optional
            }
        }
        request.setPayload(proto.getPayload());
        return request;
    }

    public com.saas.notificationservice.proto.NotificationResponse toNotificationProto(com.saas.notificationservice.dto.NotificationResponse dto) {
        com.saas.notificationservice.proto.NotificationResponse.Builder builder = com.saas.notificationservice.proto.NotificationResponse.newBuilder()
                .setId(dto.getId())
                .setCustomerId(dto.getCustomerId())
                .setTemplateId(dto.getTemplateId())
                .setChannel(toNotificationChannelProto(dto.getChannel()))
                .setStatus(toNotificationStatusProto(dto.getStatus()));
        
        if (dto.getScheduledAt() != null) {
            builder.setScheduledAt(dto.getScheduledAt().format(FORMATTER));
        }
        if (dto.getSentAt() != null) {
            builder.setSentAt(dto.getSentAt().format(FORMATTER));
        }
        if (dto.getPayload() != null) {
            builder.setPayload(dto.getPayload());
        }
        if (dto.getCreatedAt() != null) {
            builder.setCreatedAt(dto.getCreatedAt().format(FORMATTER));
        }
        
        return builder.build();
    }

    public NotificationRequest toNotificationRequest(UpdateNotificationRequest proto) {
        NotificationRequest request = new NotificationRequest();
        request.setCustomerId(proto.getCustomerId());
        request.setTemplateId(proto.getTemplateId());
        request.setChannel(toNotificationChannel(proto.getChannel()));
        request.setStatus(toNotificationStatus(proto.getStatus()));
        if (!proto.getScheduledAt().isEmpty()) {
            try {
                request.setScheduledAt(LocalDateTime.parse(proto.getScheduledAt(), FORMATTER));
            } catch (Exception e) {
                // If parsing fails, leave scheduledAt as null
            }
        }
        request.setPayload(proto.getPayload());
        return request;
    }

    // Channel Config conversions
    public ChannelConfigRequest toChannelConfigRequest(CreateChannelConfigRequest proto) {
        ChannelConfigRequest request = new ChannelConfigRequest();
        request.setChannel(toNotificationChannel(proto.getChannel()));
        request.setConfigJson(proto.getConfigJson());
        request.setIsActive(proto.getIsActive());
        return request;
    }

    public com.saas.notificationservice.proto.ChannelConfigResponse toChannelConfigProto(com.saas.notificationservice.dto.ChannelConfigResponse dto) {
        com.saas.notificationservice.proto.ChannelConfigResponse.Builder builder = com.saas.notificationservice.proto.ChannelConfigResponse.newBuilder()
                .setId(dto.getId())
                .setChannel(toNotificationChannelProto(dto.getChannel()))
                .setConfigJson(dto.getConfigJson())
                .setIsActive(dto.getIsActive());
        
        if (dto.getCreatedAt() != null) {
            builder.setCreatedAt(dto.getCreatedAt().format(FORMATTER));
        }
        
        return builder.build();
    }

    public ChannelConfigRequest toChannelConfigRequest(UpdateChannelConfigRequest proto) {
        ChannelConfigRequest request = new ChannelConfigRequest();
        request.setChannel(toNotificationChannel(proto.getChannel()));
        request.setConfigJson(proto.getConfigJson());
        request.setIsActive(proto.getIsActive());
        return request;
    }

    // Preferences conversions
    public SubscriberPreferencesRequest toPreferencesRequest(CreatePreferencesRequest proto) {
        SubscriberPreferencesRequest request = new SubscriberPreferencesRequest();
        request.setCustomerId(proto.getCustomerId());
        request.setEmailEnabled(proto.getEmailEnabled());
        request.setSmsEnabled(proto.getSmsEnabled());
        request.setPushEnabled(proto.getPushEnabled());
        request.setLanguage(proto.getLanguage());
        return request;
    }

    public PreferencesResponse toPreferencesProto(com.saas.notificationservice.dto.SubscriberPreferencesResponse dto) {
        PreferencesResponse.Builder builder = PreferencesResponse.newBuilder()
                .setId(dto.getId())
                .setCustomerId(dto.getCustomerId())
                .setEmailEnabled(dto.getEmailEnabled())
                .setSmsEnabled(dto.getSmsEnabled())
                .setPushEnabled(dto.getPushEnabled());
        
        if (dto.getLanguage() != null) {
            builder.setLanguage(dto.getLanguage());
        }
        if (dto.getUpdatedAt() != null) {
            builder.setUpdatedAt(dto.getUpdatedAt().format(FORMATTER));
        }
        
        return builder.build();
    }

    public SubscriberPreferencesRequest toPreferencesRequest(UpdatePreferencesRequest proto) {
        SubscriberPreferencesRequest request = new SubscriberPreferencesRequest();
        request.setCustomerId(proto.getCustomerId());
        request.setEmailEnabled(proto.getEmailEnabled());
        request.setSmsEnabled(proto.getSmsEnabled());
        request.setPushEnabled(proto.getPushEnabled());
        request.setLanguage(proto.getLanguage());
        return request;
    }

    // Enum conversions
    private TemplateType toTemplateType(TemplateTypeProto proto) {
        return switch (proto) {
            case TEMPLATE_EMAIL -> TemplateType.EMAIL;
            case TEMPLATE_SMS -> TemplateType.SMS;
            case TEMPLATE_PUSH -> TemplateType.PUSH;
            default -> TemplateType.EMAIL;
        };
    }

    private TemplateTypeProto toTemplateTypeProto(TemplateType type) {
        return switch (type) {
            case EMAIL -> TemplateTypeProto.TEMPLATE_EMAIL;
            case SMS -> TemplateTypeProto.TEMPLATE_SMS;
            case PUSH -> TemplateTypeProto.TEMPLATE_PUSH;
        };
    }

    private NotificationChannel toNotificationChannel(NotificationChannelProto proto) {
        return switch (proto) {
            case CHANNEL_EMAIL -> NotificationChannel.EMAIL;
            case CHANNEL_SMS -> NotificationChannel.SMS;
            case CHANNEL_PUSH -> NotificationChannel.PUSH;
            default -> NotificationChannel.EMAIL;
        };
    }

    private NotificationChannelProto toNotificationChannelProto(NotificationChannel channel) {
        return switch (channel) {
            case EMAIL -> NotificationChannelProto.CHANNEL_EMAIL;
            case SMS -> NotificationChannelProto.CHANNEL_SMS;
            case PUSH -> NotificationChannelProto.CHANNEL_PUSH;
        };
    }

    private NotificationStatus toNotificationStatus(NotificationStatusProto proto) {
        return switch (proto) {
            case STATUS_QUEUED -> NotificationStatus.QUEUED;
            case STATUS_SENT -> NotificationStatus.SENT;
            case STATUS_FAILED -> NotificationStatus.FAILED;
            default -> NotificationStatus.QUEUED;
        };
    }

    private NotificationStatusProto toNotificationStatusProto(NotificationStatus status) {
        return switch (status) {
            case QUEUED -> NotificationStatusProto.STATUS_QUEUED;
            case SENT -> NotificationStatusProto.STATUS_SENT;
            case FAILED -> NotificationStatusProto.STATUS_FAILED;
        };
    }
}

