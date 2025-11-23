package com.customer.service.infrastructure.persistence.mappers;

import com.customer.service.domain.entities.Customer;
import com.customer.service.domain.valueobjects.Email;
import com.customer.service.domain.valueobjects.PhoneNumber;
import com.customer.service.infrastructure.persistence.entities.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerEntityMapper {
    
    public Customer toDomain(CustomerEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Customer.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(new Email(entity.getEmail()))
                .phone(new PhoneNumber(entity.getPhone()))
                .companyId(entity.getCompanyId())
                .billingAddressId(entity.getBillingAddressId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
    
    public CustomerEntity toEntity(Customer domain) {
        if (domain == null) {
            return null;
        }
        
        return CustomerEntity.builder()
                .id(domain.getId())
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .email(domain.getEmail() != null ? domain.getEmail().getValue() : null)
                .phone(domain.getPhone() != null ? domain.getPhone().getValue() : null)
                .companyId(domain.getCompanyId())
                .billingAddressId(domain.getBillingAddressId())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}

