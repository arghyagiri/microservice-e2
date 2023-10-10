package com.tcs.training.finance.service;

import com.tcs.traning.shared.event.impl.DatabaseEventActions;
import com.tcs.traning.shared.event.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinanceEventProcessorService {

	private final DatabaseEventActions databaseEventActions;

	@Value("${spring.application.name}")
	private String applicationName;

	Thread eventListenerThread = new Thread(() -> {
		while (true) {
			try {

				Event event = databaseEventActions.consume(applicationName);

				if (event != null) {
					databaseEventActions.markAsProcessed(event);
				}

				// Sleep for a while before polling again
				Thread.sleep(5000);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	});

	public void process() {
		eventListenerThread.start();
	}

}
