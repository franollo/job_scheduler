package test.java.dao.jpa;

import main.java.dao.model.ProductDAO;
import main.java.dao.model.ProductOperationDAO;
import main.java.model.*;
import org.hibernate.exception.GenericJDBCException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

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
    private ProductDAO productDAO;

    @Test
    public void performTest() {
        Product product = new Product();
        product.setName("unit_test");
        product.setGroupId(1);
        Set<ProductOperation> productOperationSet;
        ProductOperation po1 = new ProductOperation();
        ProductOperation po2 = new ProductOperation();
        po1.setName("unit_test_po1");
        po2.setName("unit_test_po2");
//        productOperationSet.add(po1);
//        productOperationSet.add(po2);
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
