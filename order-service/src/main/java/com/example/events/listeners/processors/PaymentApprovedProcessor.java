package com.example.events.listeners.processors;

import com.example.domain.services.OrderService;
import com.example.events.converter.DataConverter;
import com.example.events.listeners.dto.Payment;
import com.example.events.processors.JsonEventProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PaymentApprovedProcessor extends JsonEventProcessor<Payment> {

    private OrderService orderService;

    public PaymentApprovedProcessor(@Qualifier("paymentConverter") DataConverter converter, OrderService orderService) {
        super(converter);
        this.orderService = orderService;
    }

    @Override
    protected void process(Payment data) {
        orderService.markAsPaymentApproved(data.getOrderId());
    }

}
