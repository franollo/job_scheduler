package main.java.dao.jpa;

import main.java.dao.model.ProductDAO;
import main.java.model.Product;
import main.java.model.ProductOperation;
import main.java.model.Resource;
import main.java.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.Comparator;
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
        return entityManager.merge(product);
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
            List<Product> products = query.setParameter("username", user.getUsername()).getResultList();
            for(Product product : products) {
                Collections.sort(product.getProductOperations(), new Comparator<ProductOperation>() {
                    @Override
                    public int compare(ProductOperation o1, ProductOperation o2) {
                        return o1.getOperationNumber() - o2.getOperationNumber();
                    }
                });
            }
            return products;
        } catch (NoResultException e) {
            return null;
        }
    }
}
