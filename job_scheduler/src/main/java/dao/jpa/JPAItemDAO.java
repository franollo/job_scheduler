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
}
