package com.customer.service.infrastructure.persistence.entities;

import com.customer.service.domain.entities.Contact;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "contacts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "customer_id", nullable = false)
    private UUID customerId;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Contact.ContactType type;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String phone;
}

