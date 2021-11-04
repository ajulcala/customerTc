package com.customer.app.controllers;

import com.customer.app.models.documents.CustomerBusiness;
import com.customer.app.services.CustomerBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/customerbusiness")
public class CustomerBusinessController {
    @Autowired
    private CustomerBusinessService service;

    @GetMapping
    public Flux<CustomerBusiness> list(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CustomerBusiness>> ver(@PathVariable String id){
        return service.findById(id).map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/ruc/{ruc}")
    public Flux<CustomerBusiness> findDni(@PathVariable String ruc){
        return service.findByRuc(ruc);
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> saveCustomerBusiness(@Valid @RequestBody CustomerBusiness customer){
        Map<String, Object> respuesta = new HashMap<String, Object>();
        return service.saveCustomerBusiness(customer).onErrorResume(t->{
            return Mono.just(t).cast(WebExchangeBindException.class)
                    .flatMap(e-> Mono.just(e.getFieldErrors()))
                    .flatMapMany(Flux::fromIterable)
                    .map(fieldError -> "EL campo "+fieldError.getField() + " "+fieldError.getDefaultMessage())
                    .collectList()
                    .flatMap(list -> {
                        respuesta.put("errors", list);
                        respuesta.put("timestamp", new Date());
                        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
                        return Mono.just(ResponseEntity.badRequest().body(respuesta));
                    });
        });
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Map<String, Object>>> updateCustomerBisuness(@PathVariable String id, @Valid @RequestBody CustomerBusiness costomer){
        return service.updateCustomerBusiness(id,costomer);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable String id){
        return service.deleteCustomerBusiness(id);
    }
}
