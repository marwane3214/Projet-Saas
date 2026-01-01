package com.customer.service.application.mappers;

import com.customer.service.api.dto.CompanyRequest;
import com.customer.service.api.dto.CompanyResponse;
import com.customer.service.domain.entities.Company;
import com.customer.service.domain.valueobjects.VatNumber;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = {VatNumber.class}
)
public interface CompanyMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "vatNumber", expression = "java(new VatNumber(request.getVatNumber()))")
    Company toDomain(CompanyRequest request);
    
    @Mapping(target = "vatNumber", expression = "java(company.getVatNumber() != null ? company.getVatNumber().getValue() : null)")
    CompanyResponse toResponse(Company company);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "vatNumber", expression = "java(request.getVatNumber() != null ? new VatNumber(request.getVatNumber()) : company.getVatNumber())")
    void updateDomain(@MappingTarget Company company, CompanyRequest request);
}

