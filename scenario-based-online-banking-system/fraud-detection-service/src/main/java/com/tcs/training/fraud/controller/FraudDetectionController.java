package com.tcs.training.fraud.controller;

import com.tcs.training.fraud.entity.FraudDetection;
import com.tcs.training.fraud.repository.FraudDetectionRepository;
import com.tcs.training.fraud.service.FraudService;
import com.tcs.training.model.account.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("frauds")
@RequiredArgsConstructor
public class FraudDetectionController {

	private final FraudService fraudService;

	private final FraudDetectionRepository fraudDetectionRepository;

	@PostMapping("analyzeTransaction")
	public boolean analyze(@RequestBody Transaction transaction) {
		return fraudService.isSuspiciousTransaction(transaction);
	}

	@GetMapping
	public List<FraudDetection> getAll() {
		return fraudDetectionRepository.findAll();
	}

}
