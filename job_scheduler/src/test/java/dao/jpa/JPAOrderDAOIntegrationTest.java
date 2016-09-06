package test.java.dao.jpa;

import main.java.dao.model.OrderDAO;
import main.java.dao.model.ProductDAO;
import main.java.model.Order;
import main.java.model.OrderProduct;
import main.java.model.Product;
import main.java.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;

import java.util.ArrayList;
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

    @Autowired
    private ProductDAO productDAO;

    private Order order;
    private User user;
    private List<Integer> ids;

    @Before
    public void beforeTest() {
        user = new User();
        user.setUsername("test1");
        user.setId(1);
        user.setGroupId(1);
        order = new Order();
        order.setName("test");
        order.setGroupId(1);
        order.setOrderProducts(new ArrayList<>());
        List<Product> products = productDAO.getUsersProducts(user);
        for(Product product : products) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setAmount(1);
            orderProduct.setProduct(product);
            orderProduct.setGroupId(1);
            order.addOrderProduct(orderProduct);
        }
        ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
    }

    @Test
    public void saveTest() {
        orderDAO.save(order);
    }

    @Test
    public void getTest() {
        List<Order> orders = orderDAO.getUsersOrders(user);
        assertEquals(2, orders.size());
    }

    @Test
    public void removeOneTest() {
        orderDAO.remove(1);
    }

    @Test
    public void removeMultTest() {
        orderDAO.multipleRemove(ids);
    }
}
