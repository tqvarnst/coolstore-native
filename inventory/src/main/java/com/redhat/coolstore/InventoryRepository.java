package com.redhat.coolstore;


import com.redhat.coolstore.model.Inventory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class InventoryRepository {

    @Inject
    private EntityManager em;


    public Inventory getById(String prodId) {
        return em.find(Inventory.class,prodId);
    }

    @Transactional
    public Long updateQuantity(String prodId, Long diff) {
        Inventory inventory = this.getById(prodId);
        inventory.setQuantity(inventory.getQuantity()+diff);
        em.merge(inventory);
        return inventory.getQuantity();
    }
}
