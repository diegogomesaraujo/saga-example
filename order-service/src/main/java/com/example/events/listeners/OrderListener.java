package com.example.events.listeners;

import com.example.ApplicationContextHolder;
import com.example.events.events.EventType;
import com.example.events.processors.EventProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
public class OrderListener {

    private ObjectMapper objectMapper;

    private Map<EventType, List<EventProcessor>> processors;

    @KafkaListener(topics = "${kafka.topics.orders-log}")
    public void consumer(ConsumerRecord<String, String> record) {
        log.info(record.value());

        try {
            var objectMapper = ApplicationContextHolder.getBean(ObjectMapper.class);

            var node = objectMapper.readTree(record.value());
            var eventText = node.get("event").asText();
            var event = EventType.valueOf(eventText.toUpperCase());

            processors.get(event).forEach(p -> p.onEvent(node.get("data")));
        } catch (JsonProcessingException e) {
            log.error(record.value(), e);
        }
    }

}
