package main.java.dao.jpa;

import main.java.dao.model.ResourceTypeDAO;
import main.java.model.ResourceType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
}
