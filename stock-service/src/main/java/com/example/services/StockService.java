package com.example.services;

import com.example.clients.OrdersRestClient;
import com.example.dto.OrderMessageDTO;
import com.example.dto.OrderStatusDTO;
import com.example.exceptions.InsufficientAmountException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
public class StockService {

    private ProductService productService;

    private OrdersRestClient ordersRestClient;

    private ObjectMapper objectMapper;

    private KafkaTemplate<String, String> kafkaTemplate;

    public StockService(ProductService productService, OrdersRestClient ordersRestClient, ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.productService = productService;
        this.ordersRestClient = ordersRestClient;
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void updateStockOrder(OrderMessageDTO orderMessage) {
        var order = ordersRestClient.find(orderMessage.getId());

        var products = order.getProducts().stream().map(productOrder -> {
            var product = productService.find(productOrder.getId());

            if(productOrder.getAmount() > product.getAmount()) {
                throw new InsufficientAmountException("Estoque sem produto");
            }

            product.setAmount(product.getAmount() - productOrder.getAmount());

            return product;
        }).collect(Collectors.toList());

        productService.updateAll(products);

        sendToBroker(orderMessage.getId(), OrderStatusDTO.FINISHED);
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
