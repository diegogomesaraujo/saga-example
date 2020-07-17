package com.example.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderMessageDTO {

    private String id;
    private OrderStatusDTO status;
    private PaymentDTO payment;

}
