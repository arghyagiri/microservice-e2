package com.tcs.training.finance.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.training.finance.config.datasource.ClientDatabase;
import com.tcs.training.finance.config.datasource.ClientDatabaseContextHolder;
import com.tcs.training.finance.entity.Tuition;
import com.tcs.training.finance.model.ScholarshipAwardedEvent;
import com.tcs.training.finance.model.StudentDTO;
import com.tcs.training.finance.repository.TuitionRepository;
import com.tcs.training.shared.event.impl.DatabaseEventQueue;
import com.tcs.training.shared.event.entity.Event;
import com.tcs.training.shared.event.model.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class FinanceEventProcessorService {

	private final DatabaseEventQueue databaseEventQueue;

	private final TuitionRepository tuitionRepository;

	@Value("${spring.application.name}")
	private String applicationName;

	@Value("${event.queue.polling-interval:5000}")
	private Long pollingInterval;

	@EventListener(ApplicationStartedEvent.class)
	public void process() {
		Thread eventListenerThread = new Thread(() -> {
			while (true) {
				try {
					ClientDatabaseContextHolder.set(ClientDatabase.SHARED);
					Event event = databaseEventQueue.consume(applicationName);

					if (event != null && "student-registration".equalsIgnoreCase(event.getEventType())) {
						log.info("Going to process event :: {}", event);
						ObjectMapper objectMapper = new ObjectMapper();
						StudentDTO student = objectMapper.readValue(event.getEventData(), StudentDTO.class);
						ClientDatabaseContextHolder.set(ClientDatabase.OWN);
						Tuition tuition = tuitionRepository.findByCourseId(student.getCourseId());
						ClientDatabaseContextHolder.set(ClientDatabase.SHARED);
						if (tuition != null) {
							ScholarshipAwardedEvent scholarshipAwardedEvent = ScholarshipAwardedEvent.builder()
								.studentId(student.getStudentId())
								.scholarshipAmount(BigDecimal.valueOf(0.02).multiply(tuition.getFeeAmount()))
								.build();
							if (meetsScholarshipCriteria(student)) {
								databaseEventQueue.publish(Event.builder()
									.eventData(objectMapper.writeValueAsString(scholarshipAwardedEvent))
									.status(Status.NEW)
									.serviceName("scholarship-service")
									.eventType("ScholarshipAwardedEvent")
									.build());

							}
							databaseEventQueue.markAsProcessed(event);
						}
						else {
							databaseEventQueue.publish(event);
						}

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
				catch (JsonMappingException e) {
					throw new RuntimeException(e);
				}
				catch (JsonProcessingException e) {
					throw new RuntimeException(e);
				}
			}
		});
		eventListenerThread.start();
		// Runtime.getRuntime().addShutdownHook(eventListenerThread);
	}

	private boolean meetsScholarshipCriteria(StudentDTO student) {
		return student.getCourseId() < 1000;
	}

	public void publish(Event event) {
		databaseEventQueue.publish(event);
	}

}
