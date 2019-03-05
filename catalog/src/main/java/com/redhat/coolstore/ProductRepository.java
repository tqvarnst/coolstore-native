package com.redhat.coolstore;


import com.redhat.coolstore.model.Product;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ProductRepository {

    @Inject
    private EntityManager em;

    public List<Product> getProducts() {
        return em.createNamedQuery("Products.findAll", Product.class).getResultList();
    }

    @Transactional
    public void create(Product product) {
        em.persist(product);
    }

    @Transactional
    public void update(Product product) {
        em.merge(product);
    }


    @Transactional
    public void remove(Long id) {
        em.remove(this.getById(id));
    }

    public Product getById(Long id) {
        return em.find(Product.class,id);
    }
}
