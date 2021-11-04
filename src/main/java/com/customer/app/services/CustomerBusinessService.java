package com.customer.app.services;

import com.customer.app.models.documents.CustomerBusiness;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface CustomerBusinessService {
    public Flux<CustomerBusiness> findAll();
    public Mono<CustomerBusiness> findById(String id);
    public Flux<CustomerBusiness> findByRuc(String ruc);

    Mono<ResponseEntity<Map<String, Object>>> saveCustomerBusiness(CustomerBusiness customerBusiness);
    Mono<ResponseEntity<Map<String, Object>>> updateCustomerBusiness(String id, CustomerBusiness customerBusiness);
    Mono<ResponseEntity<Void>> deleteCustomerBusiness(String id);
}
