package main.java.dao.jpa;

import main.java.dao.model.OrderDAO;
import main.java.model.Order;
import main.java.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

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

    @Override
    public List<Order> getUserOrders(User user) {
        String queryString = "SELECT o from Order o inner join User u " +
                "on o.group.groupId = u.group.groupId " +
                "where u.username = :username";
        TypedQuery<Order> query = entityManager.createQuery(queryString, Order.class);
        try {
            return query.setParameter("username", user.getUsername()).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
