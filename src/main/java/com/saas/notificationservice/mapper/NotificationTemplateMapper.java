package com.saas.notificationservice.mapper;

import com.saas.notificationservice.dto.NotificationTemplateRequest;
import com.saas.notificationservice.dto.NotificationTemplateResponse;
import com.saas.notificationservice.entity.NotificationTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationTemplateMapper {

    NotificationTemplate toEntity(NotificationTemplateRequest request);

    NotificationTemplateResponse toResponse(NotificationTemplate entity);

    List<NotificationTemplateResponse> toResponseList(List<NotificationTemplate> entities);

    void updateEntityFromRequest(NotificationTemplateRequest request, @MappingTarget NotificationTemplate entity);
}

