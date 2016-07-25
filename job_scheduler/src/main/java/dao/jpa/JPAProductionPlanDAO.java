package main.java.dao.jpa;

import main.java.dao.model.ProductionPlanDAO;
import main.java.model.ProductionPlan;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
}
