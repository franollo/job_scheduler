package main.java.dao.jpa;

import main.java.dao.model.ProductDAO;
import main.java.model.Product;
import main.java.model.ProductOperation;
import main.java.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

/**
 * Created by Marcin Frankowski on 15.07.16.
 */

@Repository("productDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class JPAProductDAO extends JPADAO implements ProductDAO {
    @Override
    public Product save(Product product) {
        Set<ProductOperation> productOperations = product.getProductOperations();
        product.setProductOperations(null);
        Product mergeProduct = entityManager.merge(product);
        for(ProductOperation productOperation : productOperations) {
            productOperation.setProductId(mergeProduct.getId());
            productOperation.setGroupId(mergeProduct.getGroupId());
        }
        mergeProduct.setProductOperations(productOperations);
        return mergeProduct;
    }

    @Override
    public void update(Product product) {
        entityManager.merge(product);
    }

    @Override
    public void remove(Product product) {
        entityManager.remove(entityManager.find(Product.class, product.getId()));
    }

    @Override
    public List<Product> getUsersProducts(User user) {
        String queryString = "SELECT p from Product p inner join User u " +
                "on p.groupId = u.groupId " +
                "where u.username = :username";
        TypedQuery<Product> query = entityManager.createQuery(queryString, Product.class);
        try {
            return query.setParameter("username", user.getUsername()).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
