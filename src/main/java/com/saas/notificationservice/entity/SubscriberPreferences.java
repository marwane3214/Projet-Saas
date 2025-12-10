package com.saas.notificationservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "subscriber_preferences")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriberPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long customerId;

    @Column(nullable = false)
    private Boolean emailEnabled;

    @Column(nullable = false)
    private Boolean smsEnabled;

    @Column(nullable = false)
    private Boolean pushEnabled;

    @Column
    private String language;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}

