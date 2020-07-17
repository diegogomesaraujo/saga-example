package com.example.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentDTO {

    private PaymentTypeDTO type;

}
