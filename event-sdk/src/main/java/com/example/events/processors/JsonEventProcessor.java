package com.example.events.processors;

import com.example.events.converter.DataConverter;

public abstract class JsonEventProcessor <T> implements EventProcessor<String> {

    private DataConverter converter;

    public JsonEventProcessor(DataConverter converter) {
        this.converter = converter;
    }

    protected abstract void process(T data);

    public void onEvent(String value) {
        process((T) converter.convert(value));
    }

}
