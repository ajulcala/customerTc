package com.customer.app.services;

import com.customer.app.models.documents.Customer;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface CustomerService {
    public Flux<Customer> findAll();
    public Mono<Customer> findById(String id);
    public Flux<Customer> findByDni(String dni);

    Mono<ResponseEntity<Map<String, Object>>> saveCustomer(Customer costomer);
    Mono<ResponseEntity<Map<String, Object>>> updateCustomer(String id, Customer costomer);
    Mono<ResponseEntity<Void>> deleteCustomer(String id);
}
