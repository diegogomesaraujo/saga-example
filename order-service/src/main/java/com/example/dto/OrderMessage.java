package com.example.dto;

import com.example.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderMessage {

    private String id;
    private OrderStatus status;
    private PaymentDTO payment;

}
