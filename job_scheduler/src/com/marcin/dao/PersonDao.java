package com.marcin.dao;

import com.marcin.model.Person;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("personDao")
@Transactional(propagation = Propagation.REQUIRED)
public class PersonDao {

    //private static final String SELECT_QUERY = "select p from Person p";

    @PersistenceContext
    private EntityManager entityManager;

//    public void setEntityManager(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }

    public void insert(Person person) {
        entityManager.persist(person);
    }

//    public List<Person> selectAll() {
//        Query query = entityManager.createQuery(SELECT_QUERY);
//        List<Person> persons = (List<Person>) query.getResultList();
//        return persons;
//    }

}
