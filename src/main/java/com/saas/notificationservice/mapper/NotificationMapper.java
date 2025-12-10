package com.saas.notificationservice.mapper;

import com.saas.notificationservice.dto.NotificationRequest;
import com.saas.notificationservice.dto.NotificationResponse;
import com.saas.notificationservice.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper {

    Notification toEntity(NotificationRequest request);

    NotificationResponse toResponse(Notification entity);

    List<NotificationResponse> toResponseList(List<Notification> entities);

    void updateEntityFromRequest(NotificationRequest request, @MappingTarget Notification entity);
}

