package com.marcin.dao.jpa;

import com.marcin.dao.model.ProductOperationDAO;
import com.marcin.model.ProductOperation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * Created by Marcin Frankowski on 14.07.2016.
 */

@Repository("productOperationDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class JPAProductOperationDAO extends JPADAO implements ProductOperationDAO {

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

    @Override
    public boolean checkGroupId(int productOperationId, int groupId) {
        String queryString = "SELECT po.productOperationId " +
                "FROM ProductOperation po " +
                "WHERE po.productOperationId = :productOperationId " +
                "AND po.group.groupId = :groupId";
        TypedQuery<Integer> query = entityManager.createQuery(queryString, Integer.class);
        try {
             query.setParameter("productOperationId", productOperationId)
                     .setParameter("groupId", groupId)
                     .getSingleResult();
        }
        catch(NoResultException e) {
            return false;
        }
        return true;
    }
}
