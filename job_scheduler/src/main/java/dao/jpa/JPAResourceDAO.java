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
 * JPA implementation of ResourceDAO interface
 * @see main.java.dao.model.ResourceDAO
 * @author Marcin Frankowski
 */


@Repository("resourceDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class JPAResourceDAO extends JPADAO implements ResourceDAO {
    @Override
    public Resource save(Resource resource) {
        return entityManager.merge(resource);
    }

    @Override
    public void remove(Resource resource) {
        entityManager.remove(entityManager.find(Resource.class, resource.getId()));
    }

    @Override
    public void multipleRemove(List<Integer> ids) {
        entityManager
                .createQuery("delete from Resource r where r.id in (:ids)")
                .setParameter("ids", ids)
                .executeUpdate();
    }

    @Override
    public List<Resource> getUsersResources(User user) {
        String queryString = "SELECT r from Resource r inner join User u " +
                "on r.groupId = u.groupId " +
                "where u.username = :username";
        TypedQuery<Resource> query = entityManager.createQuery(queryString, Resource.class);
        try {
            return query.setParameter("username", user.getUsername()).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
