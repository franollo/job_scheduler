package test.java.dao.jpa;

import main.java.dao.model.OrderDAO;
import main.java.dao.model.ProductionPlanDAO;
import main.java.model.Order;
import main.java.model.ProductionPlan;
import main.java.model.User;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:WEB-INF/test/test-context.xml")
@Transactional
public class JPAProductionPlanDAOIntegrationTest {
    @Autowired
    private ProductionPlanDAO productionPlanDAO;

    @Autowired
    private OrderDAO orderDAO;

    private ProductionPlan productionPlan;
    private ProductionPlan productionPlan2;
    private User user;

    @Before
    public void beforeTest() {
        user = new User();
        user.setUsername("test1");
        List<Order> orders = orderDAO.getUsersOrders(user);
        productionPlan = new ProductionPlan();
        productionPlan.setId(1);
        productionPlan.setName("test");
        productionPlan.setStart(LocalDateTime.now());
        productionPlan.setEnd(LocalDateTime.now());
        productionPlan.setEditedOn(LocalDateTime.now());
        productionPlan.setCreatedOn(LocalDateTime.now());
        productionPlan.setGroupId(1);
        productionPlan.setOrders(new HashSet<>(orders));
        productionPlan2 = new ProductionPlan();
        productionPlan2.setId(2);
        productionPlan2.setName("test");
        productionPlan2.setStart(LocalDateTime.now());
        productionPlan2.setEnd(LocalDateTime.now());
        productionPlan2.setEditedOn(LocalDateTime.now());
        productionPlan2.setCreatedOn(LocalDateTime.now());
        productionPlan2.setGroupId(1);
    }

    @Test
    public void saveExistingTest() {
        productionPlanDAO.save(productionPlan);
    }

    @Test
    public void saveNewTest() {
        productionPlanDAO.save(productionPlan2);
    }

    @Test
    public void getUsersPlansTest() {
        List<ProductionPlan> orders = productionPlanDAO.getUsersProductionPlans(user);
        assertEquals(1, orders.size());
    }

    @Test
    @Ignore
    public void removeTest() {
        productionPlanDAO.remove(productionPlan);
    }
}
