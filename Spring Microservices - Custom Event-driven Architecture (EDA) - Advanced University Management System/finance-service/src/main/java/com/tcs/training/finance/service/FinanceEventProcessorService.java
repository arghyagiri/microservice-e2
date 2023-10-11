package com.tcs.training.finance.service;

import com.tcs.training.shared.event.impl.DatabaseEventQueue;
import com.tcs.training.shared.event.entity.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FinanceEventProcessorService {

	private final DatabaseEventQueue databaseEventQueue;

	@Value("${spring.application.name}")
	private String applicationName;

	@Value("${event.queue.polling-interval:5000}")
	private Long pollingInterval;

	@EventListener(ApplicationStartedEvent.class)
	public void process() {
		Thread eventListenerThread = new Thread(() -> {
			while (true) {
				try {

					Event event = databaseEventQueue.consume(applicationName);

					if (event != null) {
						log.info("Going to process event :: {}", event);
						databaseEventQueue.markAsProcessed(event);
					}
					else {
						log.info("No new event to process");
					}

					// Sleep for a while before polling again
					Thread.sleep(pollingInterval);
				}
				catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		});
		eventListenerThread.start();
		// Runtime.getRuntime().addShutdownHook(eventListenerThread);
	}

	public void publish(Event event) {
		databaseEventQueue.publish(event);
	}

}
