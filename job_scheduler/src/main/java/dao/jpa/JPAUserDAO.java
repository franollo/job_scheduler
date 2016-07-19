package main.java.dao.jpa;

import main.java.dao.model.UserDAO;
import main.java.model.User;
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
        }
        catch(NoResultException e) {
            return null;
        }
        return user;
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
//        Root<User> userRoot = criteriaQuery.from(User.class);
//        criteriaQuery.select(userRoot).where(criteriaBuilder.equal(userRoot.get("username"), username));
//        TypedQuery<User> query1 = entityManager.createQuery(criteriaQuery);
//        return query1.getSingleResult();
    }
}
