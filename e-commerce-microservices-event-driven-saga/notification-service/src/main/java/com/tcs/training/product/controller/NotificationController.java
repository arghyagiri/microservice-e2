package com.tcs.training.product.controller;

import com.tcs.training.product.entity.Notification;
import com.tcs.training.product.model.ProductDTO;
import com.tcs.training.product.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
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

	@GetMapping(value = "/get-by-ids")
	public List<Notification> getByIds(@RequestParam("id") Set<Long> ids) {
		return notificationService.getByIds(ids);
	}

	@PostMapping()
	public Notification add(@RequestBody ProductDTO author) {
		Notification notificationEntity = new Notification();
		BeanUtils.copyProperties(author, notificationEntity);
		return notificationService.add(notificationEntity);
	}

	@PutMapping()
	public Notification put(@RequestBody Notification notification) {
		return notificationService.put(notification);
	}

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable("id") Long id) {
		notificationService.delete(id);
	}

}
