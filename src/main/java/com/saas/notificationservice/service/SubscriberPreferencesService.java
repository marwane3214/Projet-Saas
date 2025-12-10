package com.saas.notificationservice.service;

import com.saas.notificationservice.dto.SubscriberPreferencesRequest;
import com.saas.notificationservice.dto.SubscriberPreferencesResponse;
import com.saas.notificationservice.entity.SubscriberPreferences;
import com.saas.notificationservice.mapper.SubscriberPreferencesMapper;
import com.saas.notificationservice.repository.SubscriberPreferencesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SubscriberPreferencesService {

    private final SubscriberPreferencesRepository repository;
    private final SubscriberPreferencesMapper mapper;

    public SubscriberPreferencesResponse create(SubscriberPreferencesRequest request) {
        if (repository.findByCustomerId(request.getCustomerId()).isPresent()) {
            throw new IllegalArgumentException("Preferences for customer ID '" + request.getCustomerId() + "' already exist");
        }
        SubscriberPreferences entity = mapper.toEntity(request);
        SubscriberPreferences saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<SubscriberPreferencesResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubscriberPreferencesResponse getById(Long id) {
        SubscriberPreferences entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subscriber preferences not found with id: " + id));
        return mapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public SubscriberPreferencesResponse getByCustomerId(Long customerId) {
        SubscriberPreferences entity = repository.findByCustomerId(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Subscriber preferences not found for customer ID: " + customerId));
        return mapper.toResponse(entity);
    }

    public SubscriberPreferencesResponse update(Long id, SubscriberPreferencesRequest request) {
        SubscriberPreferences entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subscriber preferences not found with id: " + id));
        
        // Check if customerId is being changed and if new customerId already exists
        if (!entity.getCustomerId().equals(request.getCustomerId()) && 
            repository.findByCustomerId(request.getCustomerId()).isPresent()) {
            throw new IllegalArgumentException("Preferences for customer ID '" + request.getCustomerId() + "' already exist");
        }
        
        mapper.updateEntityFromRequest(request, entity);
        SubscriberPreferences updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Subscriber preferences not found with id: " + id);
        }
        repository.deleteById(id);
    }
}

