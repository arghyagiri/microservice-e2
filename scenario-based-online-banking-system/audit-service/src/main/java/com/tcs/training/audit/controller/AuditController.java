package com.tcs.training.audit.controller;

import com.tcs.training.audit.entity.Audit;
import com.tcs.training.audit.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("audits")
@RequiredArgsConstructor
public class AuditController {

	private final AuditService auditService;

	@GetMapping
	public List<Audit> getAll() {
		return auditService.getAll();
	}

	@GetMapping(value = "/{id}")
	public Audit getById(@PathVariable("id") Long id) {
		return auditService.getById(id);
	}

	@PostMapping("/log-activity")
	public com.tcs.training.model.audit.Audit add(@RequestBody com.tcs.training.model.audit.Audit audit) {
		Audit auditEntity = new Audit();
		BeanUtils.copyProperties(audit, auditEntity);
		auditEntity = auditService.add(auditEntity);
		BeanUtils.copyProperties(auditEntity, audit);
		return audit;
	}

}
