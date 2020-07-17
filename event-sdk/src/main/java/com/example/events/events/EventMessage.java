package com.example.events.events;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EventMessage {

    private EventType type;

    private Object data;

}
