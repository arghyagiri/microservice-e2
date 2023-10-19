package com.tcs.training.audit.repository;

import com.tcs.training.audit.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<Audit, Long> {

}
