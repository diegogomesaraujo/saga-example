package com.example.events.listeners.processors;

import com.example.domain.services.OrderService;
import com.example.events.converter.DataConverter;
import com.example.events.listeners.dto.Payment;
import com.example.events.processors.JsonEventProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PaymentRefusedProcessor extends JsonEventProcessor<Payment> {

    private OrderService orderService;

    public PaymentRefusedProcessor(@Qualifier("paymentConverter") DataConverter converter, OrderService orderService) {
        super(converter);
        this.orderService = orderService;
    }

    @Override
    protected void process(Payment payment) {
        orderService.markAsPaymentRefused(payment.getOrderId());
    }

}
