package com.customer.service.api.dto;

import com.customer.service.domain.entities.Contact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponse {
    private UUID id;
    private UUID customerId;
    private Contact.ContactType type;
    private String email;
    private String phone;
}

