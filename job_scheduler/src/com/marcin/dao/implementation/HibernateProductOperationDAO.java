package com.marcin.dao.implementation;

import com.marcin.dao.ProductOperationDAO;
import com.marcin.model.ProductOperation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Marcin Frankowski on 14.07.2016.
 */
@Repository("productOperationDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class HibernateProductOperationDAO implements ProductOperationDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insert(ProductOperation productOperation) {
        entityManager.persist(productOperation);
    }

    @Override
    public void update(ProductOperation productOperation) {
        entityManager.merge(productOperation);
    }

    @Override
    public void delete(ProductOperation productOperation) {
        entityManager.remove(productOperation);
    }
}
