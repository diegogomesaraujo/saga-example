package com.example.resources;

import com.example.domain.Order;
import com.example.dto.OrderDTO;
import com.example.dto.OrderResponseDTO;
import com.example.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrdersResource {

    private OrderService service;

    private ModelMapper mapper;

    private HttpServletRequest request;

    public OrdersResource(OrderService service, ModelMapper mapper, HttpServletRequest request) {
        this.service = service;
        this.mapper = mapper;
        this.request = request;
    }

    @GetMapping("/{id}")
    public Order find(@PathVariable String id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public OrderResponseDTO create(@RequestBody OrderDTO dto) {
        var order = mapper.map(dto, Order.class);

        return OrderResponseDTO.builder()
                .order(service.create(order, dto.getPaymentDTO()))
                .links(List.of(String.format("%s/%s", urlBase(), order.getId())))
                .build();
    }

    private String urlBase() {
        return request.getRequestURL().toString();
    }

}
