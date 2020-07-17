package com.example.events.listeners.converters;

import com.example.events.converter.DataConverter;
import com.example.events.converter.DataConverterException;
import com.example.events.listeners.dto.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PaymentConverter implements DataConverter<Payment, String> {

    private ObjectMapper objectMapper;

    @Override
    public Payment convert(String json) {
        try {
            return objectMapper.readValue(json, Payment.class);
        } catch (JsonProcessingException e) {
            throw new DataConverterException(e);
        }
    }

}
