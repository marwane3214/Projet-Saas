package com.customer.service.domain.entities;

import com.customer.service.domain.valueobjects.Email;
import com.customer.service.domain.valueobjects.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private UUID id;
    private String firstName;
    private String lastName;
    private Email email;
    private PhoneNumber phone;
    private UUID companyId;
    private UUID billingAddressId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public void updateEmail(Email email) {
        this.email = email;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void updatePhone(PhoneNumber phone) {
        this.phone = phone;
        this.updatedAt = LocalDateTime.now();
    }
}

