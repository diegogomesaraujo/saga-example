package com.example.events.processors;

public interface EventProcessor <T> {

    public void onEvent(T value);

}
