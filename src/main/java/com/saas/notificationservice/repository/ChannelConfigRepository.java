package com.saas.notificationservice.repository;

import com.saas.notificationservice.entity.ChannelConfig;
import com.saas.notificationservice.enums.NotificationChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelConfigRepository extends JpaRepository<ChannelConfig, Long> {
    Optional<ChannelConfig> findByChannel(NotificationChannel channel);
    List<ChannelConfig> findByIsActive(Boolean isActive);
}

