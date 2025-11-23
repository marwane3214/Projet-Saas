package com.customer.service.application.mappers;

import com.customer.service.api.dto.BillingAddressRequest;
import com.customer.service.api.dto.BillingAddressResponse;
import com.customer.service.domain.entities.BillingAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BillingAddressMapper {
    
    @Mapping(target = "id", ignore = true)
    BillingAddress toDomain(BillingAddressRequest request);
    
    BillingAddressResponse toResponse(BillingAddress billingAddress);
    
    @Mapping(target = "id", ignore = true)
    void updateDomain(@MappingTarget BillingAddress billingAddress, BillingAddressRequest request);
}

