package com.tcs.training.notification.controller;

import com.tcs.training.notification.entity.Notification;
import com.tcs.training.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("notifications")
@RequiredArgsConstructor
public class NotificationController {

	private final NotificationService notificationService;

	@GetMapping
	public List<Notification> getAll() {
		return notificationService.getAll();
	}

	@GetMapping(value = "/{id}")
	public Notification getById(@PathVariable("id") UUID id) {
		return notificationService.getById(id);
	}

	// @GetMapping("/test-email")
	public String sendTestEmail() {
		notificationService.sendSimpleEmail("notifycustomerfraud@example.com", "Test Email",
				"Hello, This is test email generated.");
		return "success";
	}

}
