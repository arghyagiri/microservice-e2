package com.tcs.training.fraud.repository;

import com.tcs.training.fraud.entity.FraudDetection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FraudDetectionRepository extends JpaRepository<FraudDetection, Long> {

	FraudDetection findByTransactionId(UUID referenceId);

}
