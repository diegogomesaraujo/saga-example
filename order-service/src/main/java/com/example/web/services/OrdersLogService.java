package com.example.web.services;

import com.example.domain.Order;
import com.example.events.events.EventType;
import com.example.events.listeners.dto.OrderMessage;
import com.example.events.producers.JsonKafkaProducer;
import com.example.web.dto.PaymentDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class OrdersLogService {

    @Value("${kafka.topics.orders-log}")
    private String topic;

    private JsonKafkaProducer jsonKafkaProducer;

    public void processPayment(Order order, PaymentDTO payment) {
        OrderMessage

        //jsonKafkaProducer.send(topic, EventType.PROCESS_PAYMENT, );
    }

}
