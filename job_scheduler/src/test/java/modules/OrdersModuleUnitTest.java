package test.java.modules;

import main.java.model.Order;
import main.java.model.OrderProduct;
import main.java.model.Product;
import main.java.modules.OrdersModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by Marcin Frankowski on 06.09.16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:WEB-INF/test/test-context.xml")
public class OrdersModuleUnitTest {
    private Order order;
    private OrdersModule ordersModule;
    private Method method;

    @Before
    public void beforeTest() {
        try {
            method = OrdersModule.class.getDeclaredMethod("mergeDuplicatedOrderProducts", Order.class);
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        order = new Order();
        ordersModule = new OrdersModule();
        Product product1 = new Product();
        Product product2 = new Product();
        product1.setId(1);
        product2.setId(2);
        product1.setProductOperations(new ArrayList<>());
        product2.setProductOperations(new ArrayList<>());
        order.setOrderProducts(new ArrayList<>());
        OrderProduct orderProduct1 = new OrderProduct();
        OrderProduct orderProduct2 = new OrderProduct();
        OrderProduct orderProduct3 = new OrderProduct();
        orderProduct1.setProduct(product1);
        orderProduct1.setAmount(2);
        orderProduct2.setProduct(product1);
        orderProduct2.setAmount(4);
        orderProduct3.setProduct(product2);
        orderProduct3.setAmount(1);
        order.addOrderProduct(orderProduct1);
        order.addOrderProduct(orderProduct2);
        order.addOrderProduct(orderProduct3);
    }

    @Test
    public void performTest() throws InvocationTargetException, IllegalAccessException {
        method.invoke(ordersModule, order);
        assertEquals(order.getOrderProducts().get(0).getAmount(), 6);
        assertEquals(order.getOrderProducts().get(0).getProduct().getId().intValue(), 1);
        assertEquals(order.getOrderProducts().get(1).getAmount(), 1);
        assertEquals(order.getOrderProducts().get(1).getProduct().getId().intValue(), 2);
    }


}
