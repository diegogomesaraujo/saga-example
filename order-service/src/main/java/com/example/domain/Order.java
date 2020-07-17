package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
@Builder
@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    private LocalDateTime date;

    private Set<Product> products;

    private List<OrderStatus> statusHistory;

    public void addStatus(OrderStatus status) {
        if(statusHistory == null) {
            statusHistory = new ArrayList<>();
        }

        statusHistory.add(status);
    }

}
