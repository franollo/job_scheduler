package main.java.dao.jpa;

import main.java.dao.model.ResourceDAO;
import main.java.model.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
}
