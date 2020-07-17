package com.example.web.dto;

import com.example.domain.PaymentStatus;
import com.example.domain.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {

    private String orderId;
    private PaymentStatus status;
    private PaymentType type;

}
