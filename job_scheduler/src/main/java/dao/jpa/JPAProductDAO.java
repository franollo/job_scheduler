package main.java.dao.jpa;

import main.java.dao.model.ProductDAO;
import main.java.model.Product;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

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
//
//    @Override
//    public boolean checkGroupId(int productId, int groupId) {
//        String queryString = "SELECT p.productId " +
//                "FROM Product p " +
//                "WHERE p.productId = :productId " +
//                "AND p.group.groupId = :groupId";
//        TypedQuery<Integer> query = entityManager.createQuery(queryString, Integer.class);
//        try {
//            query.setParameter("productId", productId)
//                    .setParameter("groupId", groupId)
//                    .getSingleResult();
//        }
//        catch(NoResultException e) {
//            return false;
//        }
//        return true;
//    }
}
