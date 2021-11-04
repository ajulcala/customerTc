package com.customer.app.services;

import com.customer.app.models.dao.CustomerBusinessDao;
import com.customer.app.models.documents.CustomerBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class CustomerBusinessServiceImpl implements CustomerBusinessService{
    @Autowired
    private CustomerBusinessDao dao;

    @Override
    public Flux<CustomerBusiness> findAll() {
        return dao.findAll();
    }

    @Override
    public Mono<CustomerBusiness> findById(String id) {
        return dao.findById(id);
    }

    @Override
    public Flux<CustomerBusiness> findByRuc(String ruc) {
        return dao.findByRuc(ruc);
    }

    @Override
    public Mono<ResponseEntity<Map<String, Object>>> saveCustomerBusiness(CustomerBusiness customerBusiness) {
        Map<String, Object> response = new HashMap<>();
        return dao.findByRuc(customerBusiness.getRuc()).collectList().flatMap( c -> {
            if(c.isEmpty()){
                customerBusiness.setType("Empresarial");
                if(customerBusiness.getCreateAt()==null){
                    customerBusiness.setCreateAt(new Date());
                }
                return dao.save(customerBusiness).flatMap(cx ->{
                    response.put("Usuario", cx);
                    response.put("Message", "Usuario registrado con éxito");
                    return Mono.just(new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK));
                });
            }else{
                response.put("Message", "Existe un usuario con el mismo RUC");
                response.put("Note", "Verifique sus Datos");
                return Mono.just(new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST));
            }
        });
    }

    @Override
    public Mono<ResponseEntity<Map<String, Object>>> updateCustomerBusiness(String id, CustomerBusiness customerBusiness) {
        Map<String, Object> response = new HashMap<>();
        return dao.findById(id).flatMap(c->{
            c.setRuc(customerBusiness.getRuc());
            c.setBusiness_name(customerBusiness.getBusiness_name());
            c.setAddress(customerBusiness.getAddress());
            return dao.save(c);
        }).map(customerUpdated->{
            response.put("Mensaje:", "Cliente actualizado");
            response.put("Customer:", customerUpdated);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }).defaultIfEmpty(new ResponseEntity<Map<String, Object>>(HttpStatus.NOT_FOUND));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteCustomerBusiness(String id) {
        return dao.findById(id).flatMap(p ->{
            return dao.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
        }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
}
