package com.example.domain.services;

import com.example.domain.Order;
import com.example.domain.Product;

import java.util.Set;

public interface OrderService {

    Order create(Set<Product> products);

    Order findById(String id);

    void markAsCanceled(String orderId);

    void markAsFinished(String orderId);

    void markAsPaymentRefused(String orderId);

    void markAsPaymentApproved(String orderId);

}
