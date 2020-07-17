package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class OrderStatus {

    private OrderStatusType type;

    private LocalDateTime date;

}
