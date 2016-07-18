package main.java.com.dao.jpa;

import main.java.com.dao.model.ResourceDAO;
import main.java.com.model.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * Created by Marcin Frankowski on 15.07.16.
 */

@Repository("resourceTypeDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class JPAResourceTypeDAO extends JPADAO implements ResourceDAO {
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
    public boolean checkGroupId(int resourceTypeId, int groupId) {
        String queryString = "SELECT rt.resourceTypeId " +
                "FROM ResourceType rt " +
                "WHERE rt.resourceTypeId = :resourceTypeId " +
                "AND rt.group.groupId = :groupId";
        TypedQuery<Integer> query = entityManager.createQuery(queryString, Integer.class);
        try {
            query.setParameter("resourceTypeId", resourceTypeId)
                    .setParameter("groupId", groupId)
                    .getSingleResult();
        }
        catch(NoResultException e) {
            return false;
        }
        return true;
    }
}
