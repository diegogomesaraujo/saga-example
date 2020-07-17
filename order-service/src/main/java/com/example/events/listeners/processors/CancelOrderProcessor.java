package com.example.events.listeners.processors;

import com.example.domain.services.OrderService;
import com.example.events.converter.DataConverter;
import com.example.events.listeners.dto.OrderMessage;
import com.example.events.processors.JsonEventProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CancelOrderProcessor extends JsonEventProcessor<OrderMessage> {

    private OrderService orderService;

    public CancelOrderProcessor(@Qualifier("orderMessageConverter") DataConverter converter, OrderService orderService) {
        super(converter);
        this.orderService = orderService;
    }

    @Override
    protected void process(OrderMessage orderMessage) {
        orderService.markAsCanceled(orderMessage.getOrderId());
    }

}
