package com.saas.notificationservice.service;

import com.saas.notificationservice.dto.NotificationTemplateRequest;
import com.saas.notificationservice.dto.NotificationTemplateResponse;
import com.saas.notificationservice.entity.NotificationTemplate;
import com.saas.notificationservice.mapper.NotificationTemplateMapper;
import com.saas.notificationservice.repository.NotificationTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationTemplateService {

    private final NotificationTemplateRepository repository;
    private final NotificationTemplateMapper mapper;

    public NotificationTemplateResponse create(NotificationTemplateRequest request) {
        if (repository.findByName(request.getName()).isPresent()) {
            throw new IllegalArgumentException("Template with name '" + request.getName() + "' already exists");
        }
        NotificationTemplate entity = mapper.toEntity(request);
        NotificationTemplate saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<NotificationTemplateResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public NotificationTemplateResponse getById(Long id) {
        NotificationTemplate entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Template not found with id: " + id));
        return mapper.toResponse(entity);
    }

    public NotificationTemplateResponse update(Long id, NotificationTemplateRequest request) {
        NotificationTemplate entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Template not found with id: " + id));
        
        // Check if name is being changed and if new name already exists
        if (!entity.getName().equals(request.getName()) && 
            repository.findByName(request.getName()).isPresent()) {
            throw new IllegalArgumentException("Template with name '" + request.getName() + "' already exists");
        }
        
        mapper.updateEntityFromRequest(request, entity);
        NotificationTemplate updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Template not found with id: " + id);
        }
        repository.deleteById(id);
    }
}

