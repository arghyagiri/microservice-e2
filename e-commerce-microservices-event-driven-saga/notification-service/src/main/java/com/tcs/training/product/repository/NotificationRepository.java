package com.tcs.training.product.repository;

import com.tcs.training.product.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Notification findByReferenceId(UUID referenceId);
}
