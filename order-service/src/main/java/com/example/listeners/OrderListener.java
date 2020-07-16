package com.example.listeners;

import com.example.dto.OrderMessage;
import com.example.services.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderListener {

    private OrderService service;

    private ObjectMapper objectMapper;

    public OrderListener(OrderService service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "orders-log")
    public void consumer(ConsumerRecord<String, String> record) {
        log.info(record.value());

        try {
            var orderMessage = objectMapper.readValue(record.value(), OrderMessage.class);

            service.updateStatus(orderMessage.getId(), orderMessage.getStatus());
        } catch (JsonProcessingException e) {
            log.error(record.value(), e);
        }
    }

}
