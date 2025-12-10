package com.saas.notificationservice.service;

import com.saas.notificationservice.dto.NotificationRequest;
import com.saas.notificationservice.dto.NotificationResponse;
import com.saas.notificationservice.entity.Notification;
import com.saas.notificationservice.mapper.NotificationMapper;
import com.saas.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

    private final NotificationRepository repository;
    private final NotificationMapper mapper;

    public NotificationResponse create(NotificationRequest request) {
        Notification entity = mapper.toEntity(request);
        Notification saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public NotificationResponse getById(Long id) {
        Notification entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found with id: " + id));
        return mapper.toResponse(entity);
    }

    public NotificationResponse update(Long id, NotificationRequest request) {
        Notification entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found with id: " + id));
        mapper.updateEntityFromRequest(request, entity);
        Notification updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Notification not found with id: " + id);
        }
        repository.deleteById(id);
    }
}

