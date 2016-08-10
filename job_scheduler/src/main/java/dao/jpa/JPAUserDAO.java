package main.java.dao.jpa;

import main.java.dao.model.UserDAO;
import main.java.exceptions.ObjectAuthorizationException;
import main.java.model.User;
import main.java.model.common.GroupObject;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Queue;

@Repository("userDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class JPAUserDAO extends JPADAO implements UserDAO {

    @Override
    public User getUserByLogin(String username) {
        User user;
        String queryString = "SELECT u FROM User u WHERE u.username = :username";
        TypedQuery<User> query = entityManager.createQuery(queryString, User.class);
        try {
            user = query.setParameter("username", username).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return user;
    }

    @Override
    public void confirmPermission(Class<? extends GroupObject> objectClass, Integer idToCheck, User user) throws ObjectAuthorizationException {
        if(idToCheck == null) {
            return;
        }
        String className = objectClass.getSimpleName();
        String queryString = "SELECT count(go.id) " +
                "FROM " + className + " go " +
                "WHERE go.id = :id " +
                "AND go.groupId = :groupId";
        Query query = entityManager.createQuery(queryString);
        Object object = query.setParameter("id", idToCheck)
                .setParameter("groupId", user.getGroupId()).getSingleResult();
        if((long)object != 1) {
            throw new ObjectAuthorizationException("User [" +
                    user.getUsername() +
                    "] is not authorized to modify [" +
                    className + "s" +
                    "] with [id] = " +
                    idToCheck);
        }
    }

    @Override
    public void confirmPermission(Class<? extends GroupObject> objectClass, List<Integer> idsToCheck, User user) throws ObjectAuthorizationException {
        if(idsToCheck.isEmpty() == true) {
            return;
        }
        String className = objectClass.getSimpleName();
        String queryString = "SELECT count(distinct go.id) " +
                "FROM " + className + " go " +
                "WHERE go.id in(:ids) " +
                "AND go.groupId = :groupId";
        Query query = entityManager.createQuery(queryString);
        Object object = query.setParameter("ids", idsToCheck)
                .setParameter("groupId", user.getGroupId()).getSingleResult();
        if((long)object != (long)idsToCheck.size()) {
            throw new ObjectAuthorizationException("User [" +
                    user.getUsername() +
                    "] is not authorized to modify [" +
                    className + "s" +
                    "] with [id] in (" +
                    idsToCheck.toString() + ")");
        }
    }

}
