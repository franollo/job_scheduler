package main.java.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Parent class for JPA DAO implementation
 * @author Marcin Frankowski
 */

public class JPADAO {
    @PersistenceContext
    protected EntityManager entityManager;
}
