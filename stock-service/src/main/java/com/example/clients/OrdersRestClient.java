package com.example.clients;

import com.example.web.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service", path = "/orders")
public interface OrdersRestClient {

    @GetMapping("{id}")
    OrderDTO find(@PathVariable String id);

}
