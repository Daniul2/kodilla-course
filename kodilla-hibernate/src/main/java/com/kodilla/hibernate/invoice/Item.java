package com.kodilla.hibernate.invoice;

import javax.persistence.*;
import java.math.BigDecimal;
@Entity
@Table(name = "ITEMS")
public class Item {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal value;
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Item() {
    }

    public Item( Product product, BigDecimal price, Integer quantity) {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        this.value = price.multiply(new BigDecimal(quantity));
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
