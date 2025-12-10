package com.saas.notificationservice.service;

import com.saas.notificationservice.dto.ChannelConfigRequest;
import com.saas.notificationservice.dto.ChannelConfigResponse;
import com.saas.notificationservice.entity.ChannelConfig;
import com.saas.notificationservice.mapper.ChannelConfigMapper;
import com.saas.notificationservice.repository.ChannelConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChannelConfigService {

    private final ChannelConfigRepository repository;
    private final ChannelConfigMapper mapper;

    public ChannelConfigResponse create(ChannelConfigRequest request) {
        if (repository.findByChannel(request.getChannel()).isPresent()) {
            throw new IllegalArgumentException("Channel config for '" + request.getChannel() + "' already exists");
        }
        ChannelConfig entity = mapper.toEntity(request);
        ChannelConfig saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<ChannelConfigResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ChannelConfigResponse getById(Long id) {
        ChannelConfig entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Channel config not found with id: " + id));
        return mapper.toResponse(entity);
    }

    public ChannelConfigResponse update(Long id, ChannelConfigRequest request) {
        ChannelConfig entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Channel config not found with id: " + id));
        
        // Check if channel is being changed and if new channel already exists
        if (!entity.getChannel().equals(request.getChannel()) && 
            repository.findByChannel(request.getChannel()).isPresent()) {
            throw new IllegalArgumentException("Channel config for '" + request.getChannel() + "' already exists");
        }
        
        mapper.updateEntityFromRequest(request, entity);
        ChannelConfig updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Channel config not found with id: " + id);
        }
        repository.deleteById(id);
    }
}

