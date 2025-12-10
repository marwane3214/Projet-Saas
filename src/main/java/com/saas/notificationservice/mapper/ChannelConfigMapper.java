package com.saas.notificationservice.mapper;

import com.saas.notificationservice.dto.ChannelConfigRequest;
import com.saas.notificationservice.dto.ChannelConfigResponse;
import com.saas.notificationservice.entity.ChannelConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChannelConfigMapper {

    ChannelConfig toEntity(ChannelConfigRequest request);

    ChannelConfigResponse toResponse(ChannelConfig entity);

    List<ChannelConfigResponse> toResponseList(List<ChannelConfig> entities);

    void updateEntityFromRequest(ChannelConfigRequest request, @MappingTarget ChannelConfig entity);
}

