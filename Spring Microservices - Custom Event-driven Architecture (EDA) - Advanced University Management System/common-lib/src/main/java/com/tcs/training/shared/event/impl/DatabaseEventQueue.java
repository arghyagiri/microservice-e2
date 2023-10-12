package com.tcs.training.shared.event.impl;

import com.tcs.training.shared.event.EventQueue;
import com.tcs.training.shared.event.model.Status;
import com.tcs.training.shared.event.repository.EventQueueRepository;
import com.tcs.training.shared.event.entity.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Component
@RequiredArgsConstructor
public class DatabaseEventQueue implements EventQueue {

    private final EventQueueRepository eventQueueRepository;

    @Override
    @Transactional
    public void publish(Event event) {
        Event eventQueue = mapEventQueueDB(event);
        eventQueue.setStatus(Status.NEW);
        eventQueue.setCreationDate(LocalDate.now());
        eventQueueRepository.save(eventQueue);
    }

    @Override
    @Transactional
    public Event consume(String serviceName) {
        List<Event> eventList = eventQueueRepository.findMyEventQueue(serviceName);
        if(eventList != null && !eventList.isEmpty()) {
            Event event = eventList.get(0);
            event.setStatus(Status.IN_PROCESS);
            return mapEventDB(eventQueueRepository.save(event));
        }
        return null;
    }

    @Override
    @Transactional
    public void markAsProcessed(Event event) {
        Event eventQueue = eventQueueRepository.getReferenceById(event.getEventId());
        if(eventQueue != null) {
            eventQueue.setStatus(Status.PROCESSED);
            eventQueueRepository.save(eventQueue);
        }
    }

    public Event mapEventQueueDB(Event event) {
        return Event.builder()
                .eventId(event.getEventId())
                .eventData(event.getEventData())
                .eventType(event.getEventType()).serviceName(event.getServiceName()).creationDate(LocalDate.now()).build();
    }

    public Event mapEventDB(Event event) {
        return Event.builder()
                .eventData(event.getEventData())
                .eventType(event.getEventType())
                .serviceName(event.getServiceName())
                .eventId(event.getEventId())
                .build();
    }
}
