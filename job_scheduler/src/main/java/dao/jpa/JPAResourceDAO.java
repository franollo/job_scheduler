package main.java.dao.jpa;

import main.java.dao.model.ResourceDAO;
import main.java.model.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

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
//
//    @Override
//    public boolean checkGroupId(int resourceId, int groupId) {
//        String queryString = "SELECT r.resourceId " +
//                "FROM Resource r " +
//                "WHERE r.resourceId = :resourceId " +
//                "AND r.group.groupId = :groupId";
//        TypedQuery<Integer> query = entityManager.createQuery(queryString, Integer.class);
//        try {
//            query.setParameter("resourceId", resourceId)
//                    .setParameter("groupId", groupId)
//                    .getSingleResult();
//        }
//        catch(NoResultException e) {
//            return false;
//        }
//        return true;
//    }
}
