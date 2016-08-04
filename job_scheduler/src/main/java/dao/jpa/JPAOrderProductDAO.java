package main.java.dao.jpa;

import main.java.dao.model.OrderProductDAO;
import main.java.model.OrderProduct;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Marcin Frankowski on 15.07.16.
 */

@Repository("orderProductDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class JPAOrderProductDAO extends JPADAO implements OrderProductDAO {
    @Override
    public void insert(OrderProduct orderProduct) {
        entityManager.persist(orderProduct);
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
