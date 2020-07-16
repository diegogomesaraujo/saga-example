package com.example.services;

import com.example.domain.Product;
import com.example.exceptions.NotFoundException;
import com.example.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Page<Product> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Product find(String id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void updateAmount(String id, Integer amount) {
        var product = find(id);

        product.setAmount(amount);
        repository.save(product);
    }

    public void updateAll(List<Product> products) {
        repository.saveAll(products);
    }

}
