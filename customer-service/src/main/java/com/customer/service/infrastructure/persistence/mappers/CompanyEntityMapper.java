package com.customer.service.infrastructure.persistence.mappers;

import com.customer.service.domain.entities.Company;
import com.customer.service.domain.valueobjects.VatNumber;
import com.customer.service.infrastructure.persistence.entities.CompanyEntity;
import org.springframework.stereotype.Component;

@Component
public class CompanyEntityMapper {
    
    public Company toDomain(CompanyEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Company.builder()
                .id(entity.getId())
                .legalName(entity.getLegalName())
                .vatNumber(new VatNumber(entity.getVatNumber()))
                .country(entity.getCountry())
                .createdAt(entity.getCreatedAt())
                .build();
    }
    
    public CompanyEntity toEntity(Company domain) {
        if (domain == null) {
            return null;
        }
        
        return CompanyEntity.builder()
                .id(domain.getId())
                .legalName(domain.getLegalName())
                .vatNumber(domain.getVatNumber() != null ? domain.getVatNumber().getValue() : null)
                .country(domain.getCountry())
                .createdAt(domain.getCreatedAt())
                .build();
    }
}

