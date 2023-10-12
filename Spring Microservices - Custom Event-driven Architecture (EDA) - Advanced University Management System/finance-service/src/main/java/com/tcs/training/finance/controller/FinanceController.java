package com.tcs.training.finance.controller;

import com.tcs.training.finance.entity.Tuition;
import com.tcs.training.finance.repository.TuitionRepository;
import com.tcs.training.finance.service.FinanceEventProcessorService;
import com.tcs.training.shared.event.entity.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("finance")
@RequiredArgsConstructor
public class FinanceController {

	private final FinanceEventProcessorService financeEventProcessorService;

	private final TuitionRepository tuitionRepository;

	@PostMapping("/publish")
	public void publish(@RequestBody Event event) {
		financeEventProcessorService.publish(event);
	}

	@PostMapping("/tuition")
	public Tuition createTuitionRecord(@RequestBody Tuition tuition) {
		tuition.setTuitionId(null);
		return tuitionRepository.save(tuition);
	}

}
