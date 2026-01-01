package com.customer.service.domain.entities;

import com.customer.service.domain.valueobjects.Email;
import com.customer.service.domain.valueobjects.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    private UUID id;
    private UUID customerId;
    private ContactType type;
    private Email email;
    private PhoneNumber phone;
    
    public enum ContactType {
        BILLING,
        TECHNICAL,
        OWNER
    }
}

