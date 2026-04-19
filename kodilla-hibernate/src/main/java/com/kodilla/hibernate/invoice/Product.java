package com.kodilla.hibernate.invoice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCTS")
public class Product {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    public Product() {
    }

    public Product(String name) {
        this.name = name;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }
}
