package com.tcs.training.notification.service;

import com.tcs.training.notification.entity.Notification;
import com.tcs.training.notification.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

	private final NotificationRepository notificationRepository;

	private final MailSender mailSender;

	public List<Notification> getAll() {
		return notificationRepository.findAll();
	}

	public Notification getById(UUID id) {
		return notificationRepository.findByReferenceId(id);
	}

	@Transactional
	public Notification add(@RequestBody Notification notification) {
		return notificationRepository.save(notification);
	}

	public void sendSimpleEmail(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("noreply@mybank.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		mailSender.send(message);
	}

}
