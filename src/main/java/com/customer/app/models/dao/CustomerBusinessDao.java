package com.customer.app.models.dao;

import com.customer.app.models.documents.CustomerBusiness;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CustomerBusinessDao extends ReactiveMongoRepository<CustomerBusiness, String> {
    Flux<CustomerBusiness> findByRuc(String ruc);
}
