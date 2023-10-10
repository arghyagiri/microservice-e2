package com.tcs.traning.shared.event.impl;

import com.tcs.traning.shared.event.EventActions;
import com.tcs.traning.shared.event.entity.EventQueue;
import com.tcs.traning.shared.event.model.Event;
import com.tcs.traning.shared.event.repository.EventQueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@RequiredArgsConstructor
@Component
public class DatabaseEventActions implements EventActions {

    private final EventQueueRepository eventQueueRepository;

    @Override
    public void publish(Event event) {
        EventQueue eventQueue = mapEventQueueDB(event);
        eventQueue.setStatus("NEW");
        eventQueueRepository.save(eventQueue);
    }

    @Override
    public Event consume(String serviceName) {
        EventQueue eventQueue = eventQueueRepository.findTopByOrderByCreationDateAndServiceNameAndStatus(serviceName, "NEW");
        eventQueue.setStatus("IN_PROCESS");
        eventQueueRepository.save(eventQueue);
        return mapEventDB(eventQueue);
    }

    @Override
    public void markAsProcessed(Event event) {
        EventQueue eventQueue = eventQueueRepository.getReferenceById(event.getEventId());
        eventQueue.setStatus("PROCESSED");
        eventQueueRepository.save(eventQueue);
    }

    public EventQueue mapEventQueueDB(Event event) {
        return EventQueue.builder()
                .eventData(event.getEventData())
                .eventType(event.getEventType()).serviceName(event.getServiceName()).creationDate(LocalDate.now()).build();
    }

    public Event mapEventDB(EventQueue eventQueue) {
        return Event.builder()
                .eventData(eventQueue.getEventData())
                .eventType(eventQueue.getEventType())
                .serviceName(eventQueue.getServiceName())
                .eventId(eventQueue.getEventId())
                .build();
    }
}
