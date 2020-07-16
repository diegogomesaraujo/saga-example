package com.example.services;

import com.example.domain.Order;
import com.example.domain.OrderStatus;
import com.example.dto.OrderMessage;
import com.example.dto.PaymentDTO;
import com.example.exceptions.NotFoundException;
import com.example.repositories.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.domain.OrderStatus.PENDING;
import static com.example.domain.OrderStatus.PROCESSING;

@Slf4j
@Service
public class OrderService {

    private OrderRepository repository;

    private ObjectMapper objectMapper;

    private KafkaTemplate<String, String> template;

    public OrderService(OrderRepository repository, ObjectMapper objectMapper, KafkaTemplate<String, String> template) {
        this.repository = repository;
        this.objectMapper = objectMapper;
        this.template = template;
    }

    public Order create(Order order, PaymentDTO payment) {
        order.setDate(LocalDateTime.now());
        order.setStatusHistory(List.of(PROCESSING));

        order = repository.save(order);

        sendMessageToBroker(order.getId(),
                order.getStatusHistory().get(order.getStatusHistory().size() - 1),
                payment);

        return order;
    }

    public void updateStatus(String id, OrderStatus status) {
        var order = findById(id);
        var lastStatus = order.getStatusHistory().get(order.getStatusHistory().size() - 1);

        if(status.equals(lastStatus)) {
            return;
        }

        order.getStatusHistory().add(status);

        repository.save(order);
    }

    public Order findById(String id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    private void sendMessageToBroker(String id, OrderStatus status, PaymentDTO payment) {
        try {
            var orderMessage = OrderMessage.builder()
                    .id(id)
                    .status(status)
                    .payment(payment)
                    .build();

            template.send("orders-log", objectMapper.writeValueAsString(orderMessage));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);

            updateStatus(id, PENDING);
        }
    }

}
