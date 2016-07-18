package main.java.com.dao.model;

import main.java.com.model.ProductionPlan;

/**
 * Created by Marcin Frankowski on 15.07.16.
 */
public interface ProductionPlanDAO {
    public void insert(ProductionPlan productionPlan);
    public void update(ProductionPlan productionPlan);
    public void delete(ProductionPlan productionPlan);
    public boolean checkGroupId(int productionPlanId, int groupId);
}
