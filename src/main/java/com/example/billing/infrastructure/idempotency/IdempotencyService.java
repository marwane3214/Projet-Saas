package com.example.billing.infrastructure.idempotency;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class IdempotencyService {
    
    // In production, use Redis or database for distributed systems
    private final ConcurrentHashMap<String, IdempotencyRecord> cache = new ConcurrentHashMap<>();
    
    @Transactional
    public boolean isDuplicate(String idempotencyKey) {
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            return false;
        }
        
        IdempotencyRecord record = cache.get(idempotencyKey);
        if (record == null) {
            return false;
        }
        
        // Check if record is still valid (e.g., within 24 hours)
        if (record.getCreatedAt().isBefore(LocalDateTime.now().minusHours(24))) {
            cache.remove(idempotencyKey);
            return false;
        }
        
        return true;
    }
    
    @Transactional
    public void recordRequest(String idempotencyKey, String resultId) {
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            return;
        }
        
        cache.put(idempotencyKey, IdempotencyRecord.builder()
                .idempotencyKey(idempotencyKey)
                .resultId(resultId)
                .createdAt(LocalDateTime.now())
                .build());
    }
    
    @Transactional
    public Optional<String> getResultId(String idempotencyKey) {
        IdempotencyRecord record = cache.get(idempotencyKey);
        return record != null ? Optional.of(record.getResultId()) : Optional.empty();
    }
}

