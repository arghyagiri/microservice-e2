package com.tcs.training.notification.service;

import com.tcs.training.notification.entity.Notification;
import com.tcs.training.notification.exception.NoDataFoundException;
import com.tcs.training.notification.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

	private final NotificationRepository notificationRepository;

	public List<Notification> getAll() {
		return notificationRepository.findAll();
	}

	public Notification getById(UUID id) {
		return notificationRepository.findByReferenceId(id);
	}

	public List<Notification> getByIds(Set<Long> ids) {
		List<Notification> notifications = notificationRepository.findAllById(ids);
		log.info("products :: {}", notifications);
		if (ids.size() != notifications.size()) {
			throw new NoDataFoundException("Requested products not available.");
		}
		return notifications;
	}

	@Transactional
	public Notification add(@RequestBody Notification notification) {
		return notificationRepository.save(notification);
	}

	@Transactional
	public Notification put(@RequestBody Notification notification) {
		return notificationRepository.save(notification);
	}

	@Transactional
	public void delete(@PathVariable("id") Long id) {
		notificationRepository.deleteById(id);
	}

}
