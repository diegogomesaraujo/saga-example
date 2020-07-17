package com.example.events.listeners;

import com.example.domain.Payment;
import com.example.web.dto.OrderMessageDTO;
import com.example.domain.services.impl.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderListener {

    private PaymentService service;

    private ObjectMapper objectMapper;

    private ModelMapper modelMapper;

    public OrderListener(PaymentService service, ObjectMapper objectMapper, ModelMapper modelMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }

    @KafkaListener(topics = "orders-log")
    public void consumer(ConsumerRecord<String, String> record) {
        log.info(record.value());

        try {
            var message = objectMapper.readValue(record.value(), OrderMessageDTO.class);

            switch (message.getStatus()) {
                case PROCESSING:
                    var payment = modelMapper.map(message.getPayment(), Payment.class);
                    service.process(message.getId(), payment);
                    break;
                case CANCELED:
                    service.cancelPayment(message.getId());
                    break;
            }

        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
