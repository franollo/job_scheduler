package main.java.modules;

import main.java.dao.model.ProductDAO;
import main.java.dao.model.UserDAO;
import main.java.model.Product;
import main.java.model.ProductOperation;
import main.java.model.User;

import java.util.List;

/**
 * Created by Marcin Frankowski on 06.09.16.
 */
public class ProductsModule {
    private ProductDAO productDAO;
    private UserDAO userDAO;

    public ProductsModule() {}

    public ProductsModule(ProductDAO productDAO, UserDAO userDAO) {
        this.productDAO = productDAO;
        this.userDAO = userDAO;
    }

    public Product saveProduct(Product product, User user) {
        userDAO.confirmPermission(Product.class, product.getId(), user);
        product.setGroupId(user.getGroupId());
        for(ProductOperation productOperation : product.getProductOperations()) {
            productOperation.setGroupId(user.getGroupId());
        }
        orderProductOperations(product);
        return productDAO.save(product);
    }

    public List<Product> getUserProducts(User user) {
        return productDAO.getUsersProducts(user);
    }

    public List<Integer> removeProducts(List<Integer> productIds, User user) {
        userDAO.confirmPermission(Product.class, productIds, user);
        productDAO.multipleRemove(productIds);
        return productIds;
    }

    public Integer removeProduct(Integer productId, User user) {
        Product product = new Product();
        product.setId(productId);
        userDAO.confirmPermission(Product.class, productId, user);
        productDAO.remove(product);
        return productId;
    }

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
