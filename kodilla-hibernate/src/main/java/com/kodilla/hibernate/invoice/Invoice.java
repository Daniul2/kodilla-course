package com.kodilla.hibernate.invoice;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "INVOICES")
public class Invoice {
    @Id
    @GeneratedValue
    private Integer id;
    @OneToMany(mappedBy = "invoice")
    private List<Item>items = new ArrayList<>();
    private String number;

    public Invoice() {
    }

    public Invoice(String number) {
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
