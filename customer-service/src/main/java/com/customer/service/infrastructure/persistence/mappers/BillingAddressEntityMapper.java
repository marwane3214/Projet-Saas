package com.customer.service.infrastructure.persistence.mappers;

import com.customer.service.domain.entities.BillingAddress;
import com.customer.service.infrastructure.persistence.entities.BillingAddressEntity;
import org.springframework.stereotype.Component;

@Component
public class BillingAddressEntityMapper {
    
    public BillingAddress toDomain(BillingAddressEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return BillingAddress.builder()
                .id(entity.getId())
                .line1(entity.getLine1())
                .line2(entity.getLine2())
                .city(entity.getCity())
                .zipCode(entity.getZipCode())
                .country(entity.getCountry())
                .build();
    }
    
    public BillingAddressEntity toEntity(BillingAddress domain) {
        if (domain == null) {
            return null;
        }
        
        return BillingAddressEntity.builder()
                .id(domain.getId())
                .line1(domain.getLine1())
                .line2(domain.getLine2())
                .city(domain.getCity())
                .zipCode(domain.getZipCode())
                .country(domain.getCountry())
                .build();
    }
}

