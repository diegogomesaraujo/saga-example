package com.example.repositories;

import com.example.domain.Payment;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PaymentRepository extends PagingAndSortingRepository<Payment, String> {

    List<Payment> findByOrderId(String orderId);

}
