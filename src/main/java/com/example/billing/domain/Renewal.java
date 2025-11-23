package com.example.billing.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "renewals", indexes = {
    @Index(name = "idx_renewal_subscription", columnList = "subscriptionId"),
    @Index(name = "idx_renewal_status", columnList = "status"),
    @Index(name = "idx_renewal_date", columnList = "renewalDate")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Renewal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long subscriptionId;

    @NotNull
    @Column(nullable = false)
    private LocalDate renewalDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private RenewalStatus status = RenewalStatus.SCHEDULED;

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column
    private LocalDateTime processedAt;

    @Column(length = 500)
    private String failureReason;

    @Column
    private Integer retryCount;

    @Version
    private Long version;

    public enum RenewalStatus {
        SCHEDULED, PROCESSED, FAILED
    }
}
