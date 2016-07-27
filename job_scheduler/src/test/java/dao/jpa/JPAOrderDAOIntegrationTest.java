package test.java.dao.jpa;

import main.java.dao.model.OrderDAO;
import main.java.model.Order;
import main.java.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Marcin Frankowski on 27.07.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:WEB-INF/test/test-context.xml")
@Transactional
public class JPAOrderDAOIntegrationTest {
    @Autowired
    private OrderDAO orderDAO;

    @Test
    public void performTest() {
        User user = new User();
        user.setUsername("admin");
        List<Order> orderList = orderDAO.getUserOrders(user);
    }
}
