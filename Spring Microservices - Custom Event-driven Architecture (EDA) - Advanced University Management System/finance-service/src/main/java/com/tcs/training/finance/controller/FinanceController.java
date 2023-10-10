package com.tcs.training.finance.controller;

import com.tcs.training.finance.service.FinanceEventProcessorService;
import com.tcs.traning.shared.event.model.Event;
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

	@PostMapping("/process")
	public void publish(@RequestBody Event event) {
		financeEventProcessorService.process();
	}

}
