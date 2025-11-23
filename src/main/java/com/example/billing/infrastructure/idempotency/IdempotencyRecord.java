package com.example.billing.infrastructure.idempotency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class IdempotencyRecord {
    private String idempotencyKey;
    private String resultId;
    private LocalDateTime createdAt;
}

