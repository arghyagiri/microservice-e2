package com.tcs.traning.shared.event;

import com.tcs.traning.shared.event.model.Event;

public interface EventActions {

    void publish(Event event);

    Event consume(String serviceName);

    void markAsProcessed(Event event);
}
