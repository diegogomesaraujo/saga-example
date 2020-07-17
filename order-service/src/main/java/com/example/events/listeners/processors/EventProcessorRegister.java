package com.example.events.listeners.processors;

import com.example.events.events.EventType;
import com.example.events.processors.EventProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EventProcessorRegister {

    @Bean
    public Map<EventType, List<EventProcessor>> registerEventsProcessor(
            @Qualifier("paymentApprovedProcessor") EventProcessor paymentApprovedProcessor,
            @Qualifier("paymentRefusedProcessor") EventProcessor paymentRefusedProcessor,
            @Qualifier("cancelOrderProcessor") EventProcessor cancelOrderProcessor,
            @Qualifier("finishOrderProcessor") EventProcessor finishOrderProcessor) {

        var processors = new HashMap<EventType, List<EventProcessor>>();

        processors.put(EventType.PAYMENT_APPROVED, List.of(paymentApprovedProcessor));
        processors.put(EventType.PAYMENT_REFUSED, List.of(paymentRefusedProcessor));
        processors.put(EventType.CANCEL_ORDER, List.of(cancelOrderProcessor));
        processors.put(EventType.FINISH_ORDER, List.of(finishOrderProcessor));

        return processors;
    }

}
