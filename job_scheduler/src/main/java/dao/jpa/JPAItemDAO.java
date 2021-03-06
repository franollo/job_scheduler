package main.java.dao.jpa;

import main.java.dao.model.ItemDAO;
import main.java.exceptions.ObjectAuthorizationException;
import main.java.model.Item;
import main.java.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * JPA mplementation of ItemDAO interface
 * @see main.java.dao.model.ItemDAO
 * @author Marcin Frankowski
 */

@Repository("itemDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class JPAItemDAO extends JPADAO implements ItemDAO {
    @Override
    public Item save(Item item) {
        return entityManager.merge(item);
    }

    @Override
    public void remove(Item item) {
        entityManager.remove(item);
    }

    @Override
    public List<Item> getUserItems(User user) {
        String queryString = "SELECT u from User u where u.username = :username";
        TypedQuery<Item> query = entityManager.createQuery(queryString, Item.class);
        try {
            return query.setParameter("username", user.getUsername()).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void removeByPlanId(Integer productionPlanId) {
        entityManager
                .createQuery("delete from Item i where i.productionPlanId in (:productionPlanId)")
                .setParameter("productionPlanId", productionPlanId)
                .executeUpdate();
    }


}
