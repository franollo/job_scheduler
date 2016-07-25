package main.java.dao.jpa;

import main.java.dao.model.ProductDAO;
import main.java.model.Product;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Marcin Frankowski on 15.07.16.
 */

@Repository("productDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class JPAProductDAO extends JPADAO implements ProductDAO {
    @Override
    public void insert(Product product) {
        entityManager.persist(product);
    }

    @Override
    public void update(Product product) {
        entityManager.merge(product);
    }

    @Override
    public void delete(Product product) {
        entityManager.remove(entityManager.find(Product.class, product.getId()));
    }
}
