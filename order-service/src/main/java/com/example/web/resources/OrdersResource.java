package com.example.web.resources;

import com.example.domain.Order;
import com.example.domain.Product;
import com.example.domain.services.OrderService;
import com.example.web.dto.OrderDTO;
import com.example.web.dto.OrderResponseDTO;
import com.example.web.services.OrdersLogService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrdersResource extends AbstractResource {

    private OrderService service;

    private OrdersLogService ordersLogService;

    private HttpServletRequest request;

    @GetMapping("/{id}")
    public Order find(@PathVariable String id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public OrderResponseDTO create(@RequestBody OrderDTO dto) {

        var products = dto.getProducts().stream()
                .map(p -> Product.builder()
                    .build())
                .collect(Collectors.toSet());

        var order = service.create(products);

        ordersLogService.processPayment(order, dto.getPaymentDTO());

        return OrderResponseDTO.builder()
                .order(order)
                .links(List.of(String.format("%s/%s", urlBase(request), order.getId())))
                .build();
    }

}
