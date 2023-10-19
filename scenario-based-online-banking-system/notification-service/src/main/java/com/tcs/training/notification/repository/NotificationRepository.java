package com.tcs.training.notification.repository;

import com.tcs.training.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	Notification findByReferenceId(UUID referenceId);

}
