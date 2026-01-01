package com.customer.service.infrastructure.persistence.mappers;

import com.customer.service.domain.entities.Contact;
import com.customer.service.domain.valueobjects.Email;
import com.customer.service.domain.valueobjects.PhoneNumber;
import com.customer.service.infrastructure.persistence.entities.ContactEntity;
import org.springframework.stereotype.Component;

@Component
public class ContactEntityMapper {
    
    public Contact toDomain(ContactEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Contact.builder()
                .id(entity.getId())
                .customerId(entity.getCustomerId())
                .type(entity.getType())
                .email(new Email(entity.getEmail()))
                .phone(new PhoneNumber(entity.getPhone()))
                .build();
    }
    
    public ContactEntity toEntity(Contact domain) {
        if (domain == null) {
            return null;
        }
        
        return ContactEntity.builder()
                .id(domain.getId())
                .customerId(domain.getCustomerId())
                .type(domain.getType())
                .email(domain.getEmail() != null ? domain.getEmail().getValue() : null)
                .phone(domain.getPhone() != null ? domain.getPhone().getValue() : null)
                .build();
    }
}

