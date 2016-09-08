package main.java.dao.model;

import main.java.model.Product;
import main.java.model.User;

import java.util.List;

/**
 * DAO interface for Product class
 * @see main.java.model.Product
 * @author Marcin Frankowski
 */
public interface ProductDAO {
    /**
     * Update product in database or insert if not exists
     * @param product entity to save
     * @return entity saved in database
     */
    public Product save(Product product);

    /**
     * Remove product from database
     * @param  product product to remove
     */
    public void remove(Product product);

    /**
     * Remove products with given list of ids from database
     * @param ids list of ids of products to remove
     */
    public void multipleRemove(List<Integer> ids);

    /**
     * Get products to which user has permission from database
     * @param user given user
     * @return list of user's products
     */
    public List<Product> getUsersProducts(User user);
}
