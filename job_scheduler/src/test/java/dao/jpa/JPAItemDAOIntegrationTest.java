package test.java.dao.jpa;

import main.java.dao.model.ItemDAO;
import main.java.model.Item;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Created by Marcin Frankowski on 01.09.16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:WEB-INF/test/test-context.xml")
@Transactional
public class JPAItemDAOIntegrationTest {
    @Autowired
    private ItemDAO itemDAO;
    private Item item;
    private Item item1;

    @Before
    public void beforeTest() {
        item = new Item();
        item.setId(1);
        item.setStart(LocalDateTime.of(2016, 8, 15, 15, 10));
        item.setEnd(LocalDateTime.of(2016, 8, 15, 15, 20));
        item.setPreparationStart(LocalDateTime.of(2016, 8, 15, 15, 0));
        item.setClassName("blue");
        item.setResourceId(1);
        item.setProductId(1);
        item.setGroupId(1);
        item.setCreatedOn(LocalDateTime.of(2016, 9, 6, 20, 44, 32));
        item.setProductionPlanId(1);
        item1 = new Item();
        item1.setStart(LocalDateTime.of(2016, 8, 15, 15, 10));
        item1.setEnd(LocalDateTime.of(2016, 8, 15, 15, 20));
        item1.setPreparationStart(LocalDateTime.of(2016, 8, 15, 15, 0));
        item1.setClassName("blue");
        item1.setResourceId(1);
        item1.setProductId(1);
        item1.setGroupId(1);
        item1.setProductionPlanId(1);
    }

    @Test
    public void saveExistingTest() {
        itemDAO.save(item);
    }

    @Test
    public void saveNewTest() {
        itemDAO.save(item1);
    }
}
