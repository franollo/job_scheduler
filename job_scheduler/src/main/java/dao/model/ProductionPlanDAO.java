package main.java.dao.model;

import main.java.model.ProductionPlan;
import main.java.model.User;

import java.util.List;

/**
 * Created by Marcin Frankowski on 15.07.16.
 */
public interface ProductionPlanDAO {
    public void insert(ProductionPlan productionPlan);
    public void update(ProductionPlan productionPlan);
    public void remove(ProductionPlan productionPlan);
    public List<ProductionPlan> getUsersProductionPlans(User user);
    public ProductionPlan getProductionPlan(int id);
}
