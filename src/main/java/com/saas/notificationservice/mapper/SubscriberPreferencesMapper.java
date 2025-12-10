package com.saas.notificationservice.mapper;

import com.saas.notificationservice.dto.SubscriberPreferencesRequest;
import com.saas.notificationservice.dto.SubscriberPreferencesResponse;
import com.saas.notificationservice.entity.SubscriberPreferences;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubscriberPreferencesMapper {

    SubscriberPreferences toEntity(SubscriberPreferencesRequest request);

    SubscriberPreferencesResponse toResponse(SubscriberPreferences entity);

    List<SubscriberPreferencesResponse> toResponseList(List<SubscriberPreferences> entities);

    void updateEntityFromRequest(SubscriberPreferencesRequest request, @MappingTarget SubscriberPreferences entity);
}

