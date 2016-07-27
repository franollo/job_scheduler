package main.java.dao.jpa;

import main.java.dao.model.ProductionPlanDAO;
import main.java.model.Order;
import main.java.model.ProductionPlan;
import main.java.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

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
    public List<ProductionPlan> getUsersProductionPlans(User user) {
        String queryString = "SELECT pp from ProductionPlan pp inner join User u " +
                "on pp.group.groupId = u.group.groupId " +
                "where u.username = :username";
        TypedQuery<ProductionPlan> query = entityManager.createQuery(queryString, ProductionPlan.class);
        try {
            return query.setParameter("username", user.getUsername()).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
