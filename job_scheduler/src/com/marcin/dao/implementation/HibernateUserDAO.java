package com.marcin.dao.implementation;

import com.marcin.dao.UserDAO;
import com.marcin.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


public class HibernateUserDAO implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

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

    @Override
    public boolean isUsersObject(Object object, String username) {
        return false;
    }
}
