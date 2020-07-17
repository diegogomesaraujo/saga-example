package com.example.events.producers;

import com.example.events.events.EventType;

public interface EventProducer {

    void send(String topic, EventType eventType, Object data);

}
