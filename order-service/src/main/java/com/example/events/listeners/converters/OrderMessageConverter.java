package com.example.events.listeners.converters;

import com.example.events.converter.DataConverter;
import com.example.events.converter.DataConverterException;
import com.example.events.listeners.dto.OrderMessage;
import com.example.events.listeners.dto.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class OrderMessageConverter implements DataConverter<OrderMessage, String> {

    private ObjectMapper objectMapper;

    @Override
    public OrderMessage convert(String json) {
        try {
            return objectMapper.readValue(json, OrderMessage.class);
        } catch (JsonProcessingException e) {
            throw new DataConverterException(e);
        }
    }

}
