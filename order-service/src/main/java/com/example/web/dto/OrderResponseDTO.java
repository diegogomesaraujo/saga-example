package com.example.web.dto;

import com.example.domain.Order;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderResponseDTO {

    private Order order;

    private List<String> links;

}
