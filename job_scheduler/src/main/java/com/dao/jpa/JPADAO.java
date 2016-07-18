package main.java.com.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Marcin Frankowski on 15.07.16.
 */
public class JPADAO {
    @PersistenceContext
    protected EntityManager entityManager;
}
