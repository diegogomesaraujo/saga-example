package com.example.web.resources;

import com.example.domain.Product;
import com.example.domain.services.impl.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/products")
public class ProductsResource {

    private ProductService service;

    public ProductsResource(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public Page<Product> list(Pageable pageable) {
        return service.list(pageable);
    }

}
