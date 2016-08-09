package test.java.dao.jpa;

import main.java.dao.model.ProductOperationDAO;
import main.java.model.*;
import org.hibernate.exception.GenericJDBCException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Marcin Frankowski on 19.07.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:WEB-INF/test/test-context.xml")
@Transactional
public class JPAProductOperationDAOIntegrationTest {
    @Autowired
    private ProductOperationDAO productOperationDAO;

    @Test
    public void performTest() {
        ProductOperation productOperation = new ProductOperation();
        productOperation.setName("test");
        productOperation.setDescription("test");
        productOperation.setDuration(10);
        productOperation.setOperationNumber(1);
        productOperation.setProductId(1);
        productOperation.setResourceTypeId(1);
        productOperation.setGroupId(1);
        try {
            productOperationDAO.insert(productOperation);
        }
        catch (GenericJDBCException e) {
            System.out.println("GOTCHA!");
        }
//
//        ProductOperation insertedPO = productOperationDAO.find(productOperationDAO.save(productOperation));
//        assertNotNull(insertedPO.getId());
//        assertTrue(insertedPO.getId() > 0);
//        assertTrue(insertedPO.getName() == "test");
//        assertTrue(insertedPO.getDescription() == "test");
//        assertTrue(insertedPO.getDuration() == 10);
//        assertTrue(insertedPO.getOperationNumber() == 1);
//        assertTrue(insertedPO.getProduct().getId() == 1);
//        assertTrue(insertedPO.getResourceType().getId() == 1);
//        assertTrue(insertedPO.getGroup().getGroupId() == 1);
    }
}
