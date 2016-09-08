package main.java.dao.jpa;

import main.java.dao.model.OrderProductDAO;
import main.java.model.OrderProduct;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * JPA implementation of OrderProductDAO interface
 * @see main.java.dao.model.OrderProductDAO
 * @author Marcin Frankowski
 */

@Repository("orderProductDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class JPAOrderProductDAO extends JPADAO implements OrderProductDAO {
    @Override
    public OrderProduct save(OrderProduct orderProduct) {
        return entityManager.merge(orderProduct);
    }

    @Override
    public void update(OrderProduct orderProduct) {
        entityManager.merge(orderProduct);
    }

    @Override
    public void remove(OrderProduct orderProduct) {
        entityManager.persist(orderProduct);
    }
}
