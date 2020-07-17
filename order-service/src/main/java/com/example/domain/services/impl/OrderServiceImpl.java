package com.example.domain.services.impl;

import com.example.domain.Order;
import com.example.domain.OrderStatus;
import com.example.domain.OrderStatusType;
import com.example.domain.Product;
import com.example.domain.exception.NotFoundException;
import com.example.domain.repositories.OrderRepository;
import com.example.domain.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

import static com.example.domain.OrderStatusType.PROCESSING;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository repository;

    @Override
    public Order create(Set<Product> products) {
        var order = Order.builder()
                .products(Collections.unmodifiableSet(products))
                .date(LocalDateTime.now())
                .build();

        order.addStatus(new OrderStatus(PROCESSING, LocalDateTime.now()));

        return repository.save(order);
    }

    @Override
    public Order findById(String id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public void markAsCanceled(String orderId) {
        updateOrderStatus(orderId, OrderStatusType.CANCELED);
    }

    public void markAsFinished(String orderId) {
        updateOrderStatus(orderId, OrderStatusType.FINISHED);
    }

    @Override
    public void markAsPaymentRefused(String orderId) {
        updateOrderStatus(orderId, OrderStatusType.PAYMENT_REFUSED);
    }

    @Override
    public void markAsPaymentApproved(String orderId) {
        updateOrderStatus(orderId, OrderStatusType.PAID_OUT);
    }

    private void updateOrderStatus(String id, OrderStatusType status) {
        var order = findById(id);
        var lastStatus = order.getStatusHistory().get(order.getStatusHistory().size() - 1);

        if(status.equals(lastStatus)) {
            return;
        }

        order.getStatusHistory().add(new OrderStatus(status, LocalDateTime.now()));

        repository.save(order);
    }

}
