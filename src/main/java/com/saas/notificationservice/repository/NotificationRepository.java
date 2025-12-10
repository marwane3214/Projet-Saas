package com.saas.notificationservice.repository;

import com.saas.notificationservice.entity.Notification;
import com.saas.notificationservice.enums.NotificationChannel;
import com.saas.notificationservice.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByCustomerId(Long customerId);
    List<Notification> findByStatus(NotificationStatus status);
    List<Notification> findByChannel(NotificationChannel channel);
    List<Notification> findByCustomerIdAndStatus(Long customerId, NotificationStatus status);
}

