package com.marcin.dao.jpa;

import com.marcin.dao.model.ProductionPlanDAO;
import com.marcin.model.ProductionPlan;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * Created by Marcin Frankowski on 15.07.16.
 */

@Repository("productionPlanDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class JPAProductionPlanDAO extends JPADAO implements ProductionPlanDAO {
    @Override
    public void insert(ProductionPlan productionPlan) {
        entityManager.persist(productionPlan);
    }

    @Override
    public void update(ProductionPlan productionPlan) {
        entityManager.merge(productionPlan);
    }

    @Override
    public void delete(ProductionPlan productionPlan) {
        entityManager.remove(productionPlan);
    }

    @Override
    public boolean checkGroupId(int productionPlanId, int groupId) {
        String queryString = "SELECT pp.productionPlanId " +
                "FROM ProductionPlan pp " +
                "WHERE pp.productionPlanId = :productionPlanId " +
                "AND pp.group.groupId = :groupId";
        TypedQuery<Integer> query = entityManager.createQuery(queryString, Integer.class);
        try {
            query.setParameter("productionPlanId", productionPlanId)
                    .setParameter("groupId", groupId)
                    .getSingleResult();
        }
        catch(NoResultException e) {
            return false;
        }
        return true;
    }
}
