package main.java.dao.jpa;

import main.java.dao.model.OrderDAO;
import main.java.model.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * Created by Marcin Frankowski on 15.07.16.
 */

@Repository("orderDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class JPAOrderDAO extends JPADAO implements OrderDAO {
    @Override
    public void insert(Order order) {
        entityManager.persist(order);
    }

    @Override
    public void update(Order order) {
        entityManager.merge(order);
    }

    @Override
    public void delete(Order order) {
        entityManager.remove(order);
    }
//
//    @Override
//    public boolean checkGroupId(int orderId, int groupId) {
//        String queryString = "SELECT o.orderId " +
//                "FROM Order o " +
//                "WHERE o.orderId = :orderId " +
//                "AND o.group.groupId = :groupId";
//        TypedQuery<Integer> query = entityManager.createQuery(queryString, Integer.class);
//        try {
//            query.setParameter("orderId", orderId)
//                    .setParameter("groupId", groupId)
//                    .getSingleResult();
//        }
//        catch(NoResultException e) {
//            return false;
//        }
//        return true;
//    }
}
