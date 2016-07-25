package main.java.dao.jpa;

import main.java.dao.model.ItemDAO;
import main.java.model.Item;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Marcin Frankowski on 15.07.16.
 */

@Repository("itemDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class JPAItemDAO extends JPADAO implements ItemDAO {
    @Override
    public void insert(Item item) {
        entityManager.merge(item);
    }

    @Override
    public void update(Item item) {
        entityManager.merge(item);
    }

    @Override
    public void delete(Item item) {
        entityManager.remove(item);
    }
}
