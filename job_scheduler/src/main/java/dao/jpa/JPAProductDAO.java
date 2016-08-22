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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    public void multipleRemove(List<Integer> ids) {
        entityManager
                .createQuery("delete from Product p where p.id in (:ids)")
                .setParameter("ids", ids)
                .executeUpdate();
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
