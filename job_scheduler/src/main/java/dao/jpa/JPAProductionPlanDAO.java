package main.java.dao.jpa;

import main.java.dao.model.ProductionPlanDAO;
import main.java.model.Order;
import main.java.model.Product;
import main.java.model.ProductionPlan;
import main.java.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
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
    public void remove(ProductionPlan productionPlan) {
        entityManager.remove(productionPlan);
    }

    @Override
    public List<ProductionPlan> getUsersProductionPlans(User user) {
        String queryString = "SELECT NEW ProductionPlan(pp.id, pp.name, pp.start, pp.end, pp.createdOn, pp.editedOn) " +
                "from ProductionPlan pp inner join User u on pp.groupId = u.groupId " +
                "where u.username = :username";
        Query query = entityManager.createQuery(queryString);
        try {
            return query.setParameter("username", user.getUsername()).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public ProductionPlan getProductionPlan(int id) {
        return entityManager.find(ProductionPlan.class, id);
    }
}
