package com.customer.app.controllers;

import com.customer.app.models.documents.Customer;
import com.customer.app.services.CustomerService;
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
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService service;

    @GetMapping
    public Flux<Customer> list(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Customer>> ver(@PathVariable String id){
        return service.findById(id).map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/dni/{dni}")
    public Flux<Customer> findDni(@PathVariable String dni){
        return service.findByDni(dni);
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> saveCustomer(@Valid @RequestBody Customer costomer){
        Map<String, Object> respuesta = new HashMap<>();
        return service.saveCustomer(costomer).onErrorResume(t->{
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
    public Mono<ResponseEntity<Map<String, Object>>> updateCustomer(@PathVariable String id, @Valid @RequestBody Customer costomer){
        return service.updateCustomer(id,costomer);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable String id){
        return service.deleteCustomer(id);
    }
}
