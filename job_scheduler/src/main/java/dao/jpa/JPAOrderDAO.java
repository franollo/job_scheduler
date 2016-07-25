package main.java.dao.jpa;

import main.java.dao.model.OrderDAO;
import main.java.model.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
}
