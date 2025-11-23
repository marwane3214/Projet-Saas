package com.customer.service.application.mappers;

import com.customer.service.api.dto.ContactRequest;
import com.customer.service.api.dto.ContactResponse;
import com.customer.service.domain.entities.Contact;
import com.customer.service.domain.valueobjects.Email;
import com.customer.service.domain.valueobjects.PhoneNumber;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = {Email.class, PhoneNumber.class}
)
public interface ContactMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", expression = "java(new Email(request.getEmail()))")
    @Mapping(target = "phone", expression = "java(new PhoneNumber(request.getPhone()))")
    Contact toDomain(ContactRequest request);
    
    @Mapping(target = "email", expression = "java(contact.getEmail() != null ? contact.getEmail().getValue() : null)")
    @Mapping(target = "phone", expression = "java(contact.getPhone() != null ? contact.getPhone().getValue() : null)")
    ContactResponse toResponse(Contact contact);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "email", expression = "java(request.getEmail() != null ? new Email(request.getEmail()) : contact.getEmail())")
    @Mapping(target = "phone", expression = "java(request.getPhone() != null ? new PhoneNumber(request.getPhone()) : contact.getPhone())")
    void updateDomain(@MappingTarget Contact contact, ContactRequest request);
}

