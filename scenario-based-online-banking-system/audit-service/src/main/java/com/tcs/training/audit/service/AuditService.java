package com.tcs.training.audit.service;

import com.tcs.training.audit.entity.Audit;
import com.tcs.training.audit.repository.AuditRepository;
import com.tcs.training.model.order.NoDataFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditService {

	private final AuditRepository auditRepository;

	public List<Audit> getAll() {
		return auditRepository.findAll();
	}

	public Audit getById(Long id) {
		return auditRepository.findById(id).orElseThrow();
	}

	public List<Audit> getByIds(Set<Long> ids) {
		List<Audit> audits = auditRepository.findAllById(ids);
		log.info("products :: {}", audits);
		if (ids.size() != audits.size()) {
			throw new NoDataFoundException("Requested products not available.");
		}
		return audits;
	}

	@Transactional
	public Audit add(@RequestBody Audit audit) {
		return auditRepository.save(audit);
	}

	@Transactional
	public Audit put(@RequestBody Audit audit) {
		return auditRepository.save(audit);
	}

	@Transactional
	public void delete(@PathVariable("id") Long id) {
		auditRepository.deleteById(id);
	}

}
