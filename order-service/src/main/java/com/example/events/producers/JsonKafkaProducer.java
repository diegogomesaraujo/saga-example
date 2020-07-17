package com.example.events.producers;

import com.example.events.events.EventMessage;
import com.example.events.events.EventType;
import com.example.events.exceptions.ErrorWriteJsonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class JsonKafkaProducer implements EventProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper objectMapper;

    @Override
    public void send(String topic, EventType eventType, Object data) {
        try {
            var event = EventMessage.builder()
                    .type(eventType)
                    .data(data)
                    .build();

            kafkaTemplate.send(topic, objectMapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            throw new ErrorWriteJsonException(e);
        }
    }

}
