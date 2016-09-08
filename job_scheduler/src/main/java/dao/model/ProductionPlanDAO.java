package main.java.dao.model;

import main.java.model.ProductionPlan;
import main.java.model.User;

import java.util.List;

/**
 * DAO interface for ProductionPlan class
 * @see main.java.model.ProductionPlan
 * @author Marcin Frankowski
 */
public interface ProductionPlanDAO {
    /**
     * Update production plan in database or insert if not exists
     * @param productionPlan entity to save
     * @return entity saved in database
     */
    public ProductionPlan save(ProductionPlan productionPlan);

    /**
     * Remove production plan from database
     * @param  productionPlan production plan to remove
     */
    public void remove(ProductionPlan productionPlan);

    /**
     * Get production plans to which user has permission from database
     * @param user given user
     * @return list of user's production plans
     */
    public List<ProductionPlan> getUsersProductionPlans(User user);

    /**
     * Get production plan with given id from database
     * @param id given production plan id
     * @return production plan with given id
     */
    public ProductionPlan getProductionPlan(int id);
}
