package com.example.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderDTO {

    private List<ProductDTO> products;
    private PaymentDTO paymentDTO;

}
