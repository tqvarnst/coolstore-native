package com.redhat.coolstore.model;

import javax.persistence.*;

@Entity
@Table(name = "inventory")
@NamedQueries({
        @NamedQuery(name = "Inventory.findAll", query = "SELECT i FROM Inventory i")
})
public class Inventory {


    @Id
    private String prodId;

    private Long quantity;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
