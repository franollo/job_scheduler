package test.java.dao.jpa;

import main.java.dao.model.ResourceDAO;
import main.java.model.Resource;
import main.java.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:WEB-INF/test/test-context.xml")
@Transactional
public class JPAResourceDAOIntegrationTest {
    @Autowired
    private ResourceDAO resourceDAO;

    private Resource resource1;
    private Resource resource2;
    private User user;

    @Before
    public void beforeTest() {
        user = new User();
        user.setUsername("test1");
        resource1 = new Resource();
        resource2 = new Resource();
        resource1.setId(1);
        resource1.setName("test1");
        resource1.setDescription("test1");
        resource1.setCostPerHour(2);
        resource1.setEfficiency(2);
        resource1.setCreatedOn(LocalDateTime.now());
        resource1.setEditedOn(LocalDateTime.now());
        resource1.setResourceTypeId(1);
        resource1.setGroupId(1);
        resource2.setName("test1");
        resource2.setDescription("test1");
        resource2.setCostPerHour(2);
        resource2.setEfficiency(2);
        resource2.setCreatedOn(LocalDateTime.now());
        resource2.setEditedOn(LocalDateTime.now());
        resource2.setResourceTypeId(1);
        resource2.setGroupId(1);
    }

    @Test
    public void saveExistingTest() {
        resourceDAO.save(resource1);
    }

    @Test
    public void saveNewTest() {
        resourceDAO.save(resource2);
    }

    @Test
    public void getUsersResources() {
        List<Resource> resources = resourceDAO.getUsersResources(user);
        assertEquals(3, resources.size());
    }

    @Test
    public void removeTest() {
        resourceDAO.remove(resource1);
    }
}
