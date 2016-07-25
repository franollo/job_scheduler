package main.java.dao.jpa;

import main.java.dao.model.UserDAO;
import main.java.model.User;
import main.java.model.common.GroupObject;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

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
    public boolean hasPermission(GroupObject groupObject, User user) {
        String className = groupObject.getClass().getSimpleName();
        String queryString = "SELECT go.id " +
                "FROM " + className + " go " +
                "WHERE go.id = :id " +
                "AND go.group.groupId = :groupId";
        TypedQuery<Integer> query = entityManager.createQuery(queryString, Integer.class);
        try {
            query.setParameter("id", groupObject.getId())
                    .setParameter("groupId", user.getGroup().getGroupId())
                    .getSingleResult();
        } catch (NoResultException e) {
            return false;
        }
        return true;
    }
}
