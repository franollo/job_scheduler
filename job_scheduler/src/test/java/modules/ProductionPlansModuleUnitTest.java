package test.java.modules;

import main.java.model.*;
import main.java.modules.ProductionPlansModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Marcin Frankowski on 06.09.16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:WEB-INF/test/test-context.xml")
public class ProductionPlansModuleUnitTest {
    private ProductionPlan productionPlan;
    private ProductionPlansModule productionPlansModule;
    private List<Item> items;


    @Before
    public void beforeTest() {
        productionPlan = new ProductionPlan();
        items = new ArrayList<>();
        Product product1 = new Product();
        Product product2 = new Product();
        product1.setProductOperations(new ArrayList<>());
        product2.setProductOperations(new ArrayList<>());
        for(int i = 0; i < 4; i++) {
            ProductOperation productOperation = new ProductOperation();
            productOperation.setDuration(Duration.ofMinutes(i * 10));
            productOperation.setResourceId(i);
            productOperation.setSequential(true);
            productOperation.setOperationNumber(i+1);
            product1.getProductOperations().add(productOperation);
        }
        for(int i = 0; i < 4; i++) {
            ProductOperation productOperation = new ProductOperation();
            productOperation.setDuration(Duration.ofMinutes((4-i) * 10));
            productOperation.setResourceId(i);
            productOperation.setSequential(true);
            productOperation.setOperationNumber(i+1);
            product2.getProductOperations().add(productOperation);
        }
        Order order1 = new Order();
        Order order2 = new Order();
        order1.setOrderProducts(new ArrayList<>());
        order2.setOrderProducts(new ArrayList<>());
        OrderProduct orderProduct1 = new OrderProduct();
        OrderProduct orderProduct2 = new OrderProduct();
        OrderProduct orderProduct3 = new OrderProduct();
        orderProduct1.setProduct(product1);
        orderProduct1.setAmount(2);
        orderProduct2.setProduct(product1);
        orderProduct2.setAmount(1);
        orderProduct3.setProduct(product2);
        orderProduct3.setAmount(1);
        order1.addOrderProduct(orderProduct1);
        order2.addOrderProduct(orderProduct2);
        order2.addOrderProduct(orderProduct3);
        Set<Order> orders = new HashSet<>();
        orders.add(order1);
        orders.add(order2);
        productionPlan.setOrders(orders);
        productionPlan.setName("test");
        LocalDateTime start = LocalDateTime.of(2016, 8, 15, 15, 0);
        productionPlan.setStart(start);
        productionPlansModule = new ProductionPlansModule();
        createItemsMap();
    }

    @Test
    public void testProcess() throws InvocationTargetException, IllegalAccessException {
        productionPlansModule.process(productionPlan);
        int index = 0;
        for(Item item : productionPlan.getItems()) {
            assertEquals(item.getStart(), items.get(index).getStart());
            assertEquals(item.getEnd(), items.get(index).getEnd());
            assertEquals(item.getResourceId(), items.get(index).getResourceId());
            index++;
        }
    }

    private void createItemsMap() {
        Item item = new Item();
        item.setStart(LocalDateTime.of(2016, 8, 15, 15, 0));
        item.setEnd(LocalDateTime.of(2016, 8, 15, 15, 10));
        item.setResourceId(1);
        items.add(item);
        item = new Item();
        item.setStart(LocalDateTime.of(2016, 8, 15, 15, 10));
        item.setEnd(LocalDateTime.of(2016, 8, 15, 15, 30));
        item.setResourceId(2);
        items.add(item);
        item = new Item();
        item.setStart(LocalDateTime.of(2016, 8, 15, 15, 30));
        item.setEnd(LocalDateTime.of(2016, 8, 15, 16, 0));
        item.setResourceId(3);
        items.add(item);
        item = new Item();
        item.setStart(LocalDateTime.of(2016, 8, 15, 15, 30));
        item.setEnd(LocalDateTime.of(2016, 8, 15, 15, 40));
        item.setResourceId(1);
        items.add(item);
        item = new Item();
        item.setStart(LocalDateTime.of(2016, 8, 15, 15, 40));
        item.setEnd(LocalDateTime.of(2016, 8, 15, 16, 0));
        item.setResourceId(2);
        items.add(item);
        item = new Item();
        item.setStart(LocalDateTime.of(2016, 8, 15, 16, 0));
        item.setEnd(LocalDateTime.of(2016, 8, 15, 16, 30));
        item.setResourceId(3);
        items.add(item);
        item = new Item();
        item.setStart(LocalDateTime.of(2016, 8, 15, 16, 0));
        item.setEnd(LocalDateTime.of(2016, 8, 15, 16, 10));
        item.setResourceId(1);
        items.add(item);
        item = new Item();
        item.setStart(LocalDateTime.of(2016, 8, 15, 16, 10));
        item.setEnd(LocalDateTime.of(2016, 8, 15, 16, 30));
        item.setResourceId(2);
        items.add(item);
        item = new Item();
        item.setStart(LocalDateTime.of(2016, 8, 15, 16, 30));
        item.setEnd(LocalDateTime.of(2016, 8, 15, 17, 0));
        item.setResourceId(3);
        items.add(item);
        item = new Item();
        item.setStart(LocalDateTime.of(2016, 8, 15, 15, 30));
        item.setEnd(LocalDateTime.of(2016, 8, 15, 16, 10));
        item.setResourceId(0);
        items.add(item);
        item = new Item();
        item.setStart(LocalDateTime.of(2016, 8, 15, 16, 10));
        item.setEnd(LocalDateTime.of(2016, 8, 15, 16, 40));
        item.setResourceId(1);
        items.add(item);
        item = new Item();
        item.setStart(LocalDateTime.of(2016, 8, 15, 16, 40));
        item.setEnd(LocalDateTime.of(2016, 8, 15, 17, 0));
        item.setResourceId(2);
        items.add(item);
        item = new Item();
        item.setStart(LocalDateTime.of(2016, 8, 15, 17, 0));
        item.setEnd(LocalDateTime.of(2016, 8, 15, 17, 10));
        item.setResourceId(3);
        items.add(item);
    }
}
