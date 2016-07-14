package com.marcin.dao;

import com.marcin.model.Product;

/**
 * Created by Marcin Frankowski on 14.07.2016.
 */
public interface ProductDAO {
    public void insert(Product product, int groupId);
    public void update(Product product);
    public void delete(Product product);
}
