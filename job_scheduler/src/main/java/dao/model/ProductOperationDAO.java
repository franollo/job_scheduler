package main.java.dao.model;

import main.java.model.ProductOperation;

/**
 * Created by Marcin Frankowski on 13.07.2016.
 */

public interface ProductOperationDAO {
    public void insert(ProductOperation productOperation);
    public void update(ProductOperation productOperation);
    public void delete(ProductOperation productOperation);
//    public boolean checkGroupId(int productOperationId, int groupId);
}
