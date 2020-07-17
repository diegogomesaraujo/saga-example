package com.example.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {

    private String id;

    private LocalDateTime date;

    private List<ProductOrderDTO> products;

    private List<OrderStatusDTO> statusHistory;

}
