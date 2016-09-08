package test.java.dao.jpa;

import main.java.dao.model.ProductDAO;
import main.java.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:WEB-INF/test/test-context.xml")
@Transactional
public class JPAProductDAOIntegrationTest {
    @Autowired
    private ProductDAO productDAO;

    private Product product1;
    private Product product2;
    private User userWith;
    private User userWithout;
    private List<Integer> ids;

    @Before
    public void beforeTest() {
        product1 = new Product();
        product1.setName("test");
        product1.setProductOperations(new ArrayList<>());
        product1.setGroupId(1);
        for(int i = 1; i < 4; i++) {
            ProductOperation productOperation = new ProductOperation();
            productOperation.setDuration(Duration.ofMinutes((i+2) * 10));
            productOperation.setResourceId(i);
            productOperation.setSequential(true);
            productOperation.setOperationNumber(i);
            productOperation.setGroupId(1);
            product1.getProductOperations().add(productOperation);
        }
        product2 = new Product();
        product2.setId(1);
        product2.setName("test_from_app");
        product2.setGroupId(1);
        userWith = new User();
        userWith.setUsername("test1");
        userWithout = new User();
        userWithout.setUsername("test2");
        ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
    }

    @Test
    public void saveNewTest() {
        productDAO.save(product1);
    }

    @Test
    public void saveExistingTest() {
        productDAO.save(product2);
    }

    @Test
    public void userWithProductsTest() {
        List<Product> products = productDAO.getUsersProducts(userWith);
        assertEquals(3, products.size());
    }

    @Test
    public void userWithoutProductsTest() {
        List<Product> products = productDAO.getUsersProducts(userWithout);
        assertEquals(0, products.size());
    }

    @Test
    public void removeOneTest() {
        productDAO.remove(product2);
    }

    @Test
    public void removeMultipleTest() {
        productDAO.multipleRemove(ids);
    }

}
