package main.java.dao.jpa;

import main.java.dao.model.ProductOperationDAO;
import main.java.model.ProductOperation;
import main.java.model.common.GroupObject;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * JPA implementation of ProductOperationDAO interface
 * @see main.java.dao.model.ProductOperationDAO
 * @author Marcin Frankowski
 */

@Repository("productOperationDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class JPAProductOperationDAO extends JPADAO implements ProductOperationDAO {

    @Override
    public ProductOperation insert(ProductOperation productOperation) {
        return entityManager.merge(productOperation);
    }

    @Override
    public void update(ProductOperation productOperation) {
        entityManager.merge(productOperation);
    }

    @Override
    public void delete(ProductOperation productOperation) {
        entityManager.remove(productOperation);
    }

    @Override
    public ProductOperation find(ProductOperation productOperation) {
        return entityManager.find(ProductOperation.class, productOperation.getId());
    }
}
