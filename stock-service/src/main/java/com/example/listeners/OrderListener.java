package com.example.listeners;

import com.example.dto.OrderMessageDTO;
import com.example.dto.OrderStatusDTO;
import com.example.exceptions.InsufficientAmountException;
import com.example.services.StockService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderListener {

    private StockService stockService;

    private ObjectMapper objectMapper;

    private KafkaTemplate<String, String> kafkaTemplate;

    public OrderListener(StockService stockService, ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.stockService = stockService;
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "orders-log")
    public void consumer(ConsumerRecord<String, String> record) {
        log.info(record.value());

        try {
            var message = objectMapper.readValue(record.value(), OrderMessageDTO.class);

            if(OrderStatusDTO.PAID_OUT.equals(message.getStatus())) {
                stockService.updateStockOrder(message);
            }
        } catch (JsonProcessingException e) {
            log.error("Erro ao processar mensagem", e);
            throw new RuntimeException(e);
        } catch (InsufficientAmountException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void sendToBroker(String orderId, OrderStatusDTO status) {
        var message = OrderMessageDTO.builder()
                .id(orderId)
                .status(status)
                .build();

        try {
            kafkaTemplate.send("orders-log", objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
    }

}
