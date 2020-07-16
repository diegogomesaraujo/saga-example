package com.example.services;

import com.example.domain.Payment;
import com.example.domain.PaymentStatus;
import com.example.dto.OrderMessageDTO;
import com.example.dto.OrderStatusDTO;
import com.example.dto.PaymentDTO;
import com.example.exceptions.NotFoundException;
import com.example.repositories.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.domain.PaymentStatus.APPROVED;
import static com.example.dto.OrderStatusDTO.PAID_OUT;

@Slf4j
@Service
public class PaymentService {

    private PaymentRepository repository;

    private KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper objectMapper;

    public PaymentService(PaymentRepository repository, KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void process(String orderId, Payment payment) {
        try {
            payment.setOrderId(orderId);
            payment.setType(payment.getType());
            payment.setPaymentDate(LocalDateTime.now());
            payment.setStatus(PaymentStatus.PENDING);
            repository.save(payment);

            payment.setId(null);
            payment.setStatus(APPROVED);
            repository.save(payment);

            sendMessageToBroker(orderId, PAID_OUT, null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);

            sendMessageToBroker(orderId, OrderStatusDTO.PENDING, null);
        }
    }

    public Payment find(String id) {
        return repository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public void updateStatus(String id, PaymentStatus status) {
        var payment = find(id);

        if(status.equals(payment.getStatus())) {
            return;
        }

        payment.setStatus(status);
        repository.save(payment);
    }

    public void cancelPayment(String orderId) {
        var payments = repository.findByOrderId(orderId);
        var payment = payments.get(payments.size() - 1);

        payment.setId(null);
        payment.setStatus(PaymentStatus.CANCELED);
        payment.setPaymentDate(LocalDateTime.now());
        repository.save(payment);
    }

    private void sendMessageToBroker(String id, OrderStatusDTO status, PaymentDTO payment) {
        try {
            var orderMessage = OrderMessageDTO.builder()
                    .id(id)
                    .status(status)
                    .payment(payment)
                    .build();

            kafkaTemplate.send("orders-log", objectMapper.writeValueAsString(orderMessage));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);

            updateStatus(id, PaymentStatus.PENDING);
        }
    }

}
