package main.java.dao.jpa;

import main.java.dao.model.ResourceDAO;
import main.java.model.ProductionPlan;
import main.java.model.Resource;
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

@Repository("resourceDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class JPAResourceDAO extends JPADAO implements ResourceDAO {
    @Override
    public void insert(Resource resource) {
        entityManager.persist(resource);
    }

    @Override
    public void update(Resource resource) {
        entityManager.merge(resource);
    }

    @Override
    public void delete(Resource resource) {
        entityManager.remove(resource);
    }

    @Override
    public List<Resource> getUsersResources(User user) {
        String queryString = "SELECT r from Resource r inner join User u " +
                "on r.group.groupId = u.group.groupId " +
                "where u.username = :username";
        TypedQuery<Resource> query = entityManager.createQuery(queryString, Resource.class);
        try {
            return query.setParameter("username", user.getUsername()).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
