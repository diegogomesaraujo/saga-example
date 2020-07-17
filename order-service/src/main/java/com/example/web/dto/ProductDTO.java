package com.example.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {

    private String id;
    private Integer amount;
    private Long price;

}
