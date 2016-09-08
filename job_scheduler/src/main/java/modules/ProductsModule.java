package main.java.modules;

import main.java.dao.model.ProductDAO;
import main.java.dao.model.UserDAO;
import main.java.model.Product;
import main.java.model.ProductOperation;
import main.java.model.User;

import java.util.List;

/**
 * Module responsible for Products processing
 * @see main.java.model.Product
 * @see main.java.dao.model.ProductDAO
 * @see main.java.dao.model.UserDAO
 * @author Marcin Frankowski
 */
public class ProductsModule {
    /**
     * Product Data Access Object
     */
    private ProductDAO productDAO;

    /**
     * User Data Access Object
     */
    private UserDAO userDAO;

    /**
     * Default constructor
     */
    public ProductsModule() {}

    /**
     * Constructor which sets Product DAO, User DAO.
     * Module must be initialized with this constructor to work properlly.
     * @param productDAO Product Data Access Object
     * @param userDAO User Access Object
     */
    public ProductsModule(ProductDAO productDAO, UserDAO userDAO) {
        this.productDAO = productDAO;
        this.userDAO = userDAO;
    }

    /**
     * Save new or existing Product in system.
     * @param product product to save
     * @param user user who wants to save product
     * @return saved Product
     */
    public Product saveProduct(Product product, User user) {
        userDAO.confirmPermission(Product.class, product.getId(), user);
        product.setGroupId(user.getGroupId());
        for(ProductOperation productOperation : product.getProductOperations()) {
            productOperation.setGroupId(user.getGroupId());
        }
        orderProductOperations(product);
        return productDAO.save(product);
    }

    /**
     * Get all products to which user has permission.
     * @param user user who wants to get products
     * @return List of user's products
     */
    public List<Product> getUserProducts(User user) {
        return productDAO.getUsersProducts(user);
    }

    /**
     * Remove products with given list of ids from system.
     * @param productIds list of ids of products to remove
     * @param user user who wants to remove products
     * @return list of removed products ids
     */
    public List<Integer> removeProducts(List<Integer> productIds, User user) {
        userDAO.confirmPermission(Product.class, productIds, user);
        productDAO.multipleRemove(productIds);
        return productIds;
    }

    /**
     * Remove product with id from system.
     * @param productId id of product to remove
     * @param user user who wants to remove product
     * @return id of removed product
     */
    public Integer removeProduct(Integer productId, User user) {
        Product product = new Product();
        product.setId(productId);
        userDAO.confirmPermission(Product.class, productId, user);
        productDAO.remove(product);
        return productId;
    }

    /**
     * Helper function. When user sends product its operation are not ordered properly.
     * This function manages product operation orderind.
     * @param product product to process
     */
    private void orderProductOperations(Product product) {
        if(product.getProductOperations() == null) {
            return;
        }
        int i = 0;
        for(ProductOperation productOperation : product.getProductOperations()) {
            if(productOperation.isSequential() == true) {
                productOperation.setOperationNumber(i++);
            }
        }
    }
}
