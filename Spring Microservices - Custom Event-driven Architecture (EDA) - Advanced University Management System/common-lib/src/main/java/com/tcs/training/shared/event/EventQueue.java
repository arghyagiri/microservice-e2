package com.tcs.training.shared.event;

import com.tcs.training.shared.event.entity.Event;

public interface EventQueue {

    void publish(Event event);

    Event consume(String serviceName);

    void markAsProcessed(Event event);
}
