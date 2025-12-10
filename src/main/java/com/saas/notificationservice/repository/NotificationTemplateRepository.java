package com.saas.notificationservice.repository;

import com.saas.notificationservice.entity.NotificationTemplate;
import com.saas.notificationservice.enums.TemplateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {
    Optional<NotificationTemplate> findByName(String name);
    List<NotificationTemplate> findByType(TemplateType type);
}

