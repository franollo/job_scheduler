package main.java.dao.model;

import main.java.model.ProductOperation;

/**
 * Created by Marcin Frankowski on 13.07.2016.
 */

public interface ProductOperationDAO {
    public ProductOperation insert(ProductOperation productOperation);
    public void update(ProductOperation productOperation);
    public void delete(ProductOperation productOperation);
    public ProductOperation find(ProductOperation productOperation);
}