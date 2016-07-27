package main.java.dao.jpa;

import main.java.dao.model.ResourceTypeDAO;
import main.java.model.Resource;
import main.java.model.ResourceType;
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

@Repository("resourceTypeDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class JPAResourceTypeDAO extends JPADAO implements ResourceTypeDAO {
    @Override
    public void insert(ResourceType resourceType) {
        entityManager.persist(resourceType);
    }

    @Override
    public void update(ResourceType resourceType) {
        entityManager.merge(resourceType);
    }

    @Override
    public void delete(ResourceType resourceType) {
        entityManager.remove(resourceType);
    }

    @Override
    public List<ResourceType> getUsersResourceTypes(User user) {
        String queryString = "SELECT rt from ResourceType rt inner join User u " +
                "on rt.group.groupId = u.group.groupId " +
                "where u.username = :username";
        TypedQuery<ResourceType> query = entityManager.createQuery(queryString, ResourceType.class);
        try {
            return query.setParameter("username", user.getUsername()).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
