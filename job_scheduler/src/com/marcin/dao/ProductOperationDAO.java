package com.marcin.dao;

import com.marcin.model.Person;
import com.marcin.model.ProductOperation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Marcin Frankowski on 13.07.2016.
 */

@Repository("productOperationDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class ProductOperationDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public void insert(ProductOperation productOperation) {
        entityManager.persist(productOperation);
    }
}
