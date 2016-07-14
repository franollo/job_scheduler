package com.marcin.dao;

import com.marcin.model.ProductOperation;

/**
 * Created by Marcin Frankowski on 13.07.2016.
 */

public interface ProductOperationDAO {
    public void insert(ProductOperation productOperation);
    public void update(ProductOperation productOperation);
    public void delete(ProductOperation productOperation);
}
