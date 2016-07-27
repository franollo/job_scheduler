package main.java.dao.model;

import main.java.model.Product;
import main.java.model.User;

import java.util.List;

/**
 * Created by Marcin Frankowski on 14.07.2016.
 */
public interface ProductDAO {
    public void insert(Product product);
    public void update(Product product);
    public void delete(Product product);
    public List<Product> getUsersProducts(User user);
}
