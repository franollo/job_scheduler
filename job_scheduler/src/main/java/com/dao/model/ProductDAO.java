package main.java.com.dao.model;

import main.java.com.model.Product;

/**
 * Created by Marcin Frankowski on 14.07.2016.
 */
public interface ProductDAO {
    public void insert(Product product);
    public void update(Product product);
    public void delete(Product product);
    public boolean checkGroupId(int productId, int groupId);
}
