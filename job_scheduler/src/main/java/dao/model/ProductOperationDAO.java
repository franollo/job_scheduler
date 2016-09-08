package main.java.dao.model;

import main.java.model.ProductOperation;
import main.java.model.common.GroupObject;
import org.hibernate.exception.GenericJDBCException;

/**
 * DAO interface for ProductOperation class
 * @see main.java.model.ProductOperation
 * @author Marcin Frankowski
 */

public interface ProductOperationDAO {
    /**
     * Insert product operation into database
     * @param productOperation entity to insert
     * @return entity saved in database
     */
    public ProductOperation insert(ProductOperation productOperation);

    /**
     * Update product operation in database
     * @param productOperation entity to update
     */
    public void update(ProductOperation productOperation);

    /**
     * Remove product operation from database
     * @param productOperation entity to remove
     */
    public void delete(ProductOperation productOperation);

    /**
     * Get product operation entity from database
     * @param productOperation entity to find
     * @return product operation
     */
    public ProductOperation find(ProductOperation productOperation);
}
