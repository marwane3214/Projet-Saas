package com.customer.service.application.mappers;

import com.customer.service.api.dto.CustomerRequest;
import com.customer.service.api.dto.CustomerResponse;
import com.customer.service.domain.entities.Customer;
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
public interface CustomerMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "email", expression = "java(new Email(request.getEmail()))")
    @Mapping(target = "phone", expression = "java(new PhoneNumber(request.getPhone()))")
    Customer toDomain(CustomerRequest request);
    
    @Mapping(target = "email", expression = "java(customer.getEmail() != null ? customer.getEmail().getValue() : null)")
    @Mapping(target = "phone", expression = "java(customer.getPhone() != null ? customer.getPhone().getValue() : null)")
    CustomerResponse toResponse(Customer customer);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "email", expression = "java(request.getEmail() != null ? new Email(request.getEmail()) : customer.getEmail())")
    @Mapping(target = "phone", expression = "java(request.getPhone() != null ? new PhoneNumber(request.getPhone()) : customer.getPhone())")
    void updateDomain(@MappingTarget Customer customer, CustomerRequest request);
}

