package com.customer.app.models.documents;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Document(collection="customer_business")
public class CustomerBusiness {
    @Id
    private String id;
    private String ruc;
    private String business_name; //razon social
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt; //fecha de creacion
    private String address;
    private String type; //tipo de persona
}
