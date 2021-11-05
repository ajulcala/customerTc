package com.customer.app.services;

import com.customer.app.models.dao.CustomerDao;
import com.customer.app.models.documents.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerDao dao;

    @Override
    public Flux<Customer> findAll() {
        return dao.findAll();
    }

    @Override
    public Mono<Customer> findById(String id) {
        return dao.findById(id);
    }

    @Override
    public Flux<Customer> findByDni(String dni) {
        return dao.findByDni(dni);
    }

    @Override
    public Mono<ResponseEntity<Map<String, Object>>> saveCustomer(Customer costomer) {
        Map<String, Object> response = new HashMap<>();
        return dao.findByDni(costomer.getDni()).collectList().flatMap( c -> {
            if(c.isEmpty()){
                costomer.setType("Personal");
                return dao.save(costomer).flatMap(cx ->{
                    response.put("Usuario", cx);
                    response.put("Message", "Usuario registrado con éxito");
                    return Mono.just(new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK));
                });
            }else{
                response.put("Message", "Existe un usuario con el mismo DNI");
                response.put("Note", "Verifique sus Datos");
                return Mono.just(new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST));
            }
        });
    }

    @Override
    public Mono<ResponseEntity<Map<String, Object>>> updateCustomer(String id, Customer customer) {
        Map<String, Object> response = new HashMap<>();
        return dao.findById(id).flatMap(c->{
            c.setName(customer.getName());
            c.setApem(customer.getApem());
            c.setApep(customer.getApep());
            c.setAddress(customer.getAddress());
            c.setBirth_date(customer.getBirth_date());
            c.setDni(customer.getDni());
            c.setType(customer.getType());
            return dao.save(c);
        }).map(customerUpdated->{
            response.put("Mensaje:", "Cliente actualizado");
            response.put("Customer:", customerUpdated);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }).defaultIfEmpty(new ResponseEntity<Map<String, Object>>(HttpStatus.NOT_FOUND));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteCustomer(String id) {
        return dao.findById(id).flatMap(p ->{
            return dao.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
        }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
}
