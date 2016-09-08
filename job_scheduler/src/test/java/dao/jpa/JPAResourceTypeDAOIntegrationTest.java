package test.java.dao.jpa;

import main.java.dao.model.ResourceTypeDAO;
import main.java.model.Resource;
import main.java.model.ResourceType;
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
public class JPAResourceTypeDAOIntegrationTest {
    @Autowired
    private ResourceTypeDAO resourceTypeDAO;

    private ResourceType resourceType1;
    private ResourceType resourceType2;
    private User user;

    @Before
    public void beforeTest() {
        user = new User();
        user.setUsername("test1");
        resourceType1 = new ResourceType();
        resourceType2 = new ResourceType();
        resourceType1.setId(1);
        resourceType1.setName("test1");
        resourceType1.setDescription("test1");
        resourceType1.setCreatedOn(LocalDateTime.now());
        resourceType1.setEditedOn(LocalDateTime.now());
        resourceType1.setGroupId(1);
        resourceType2.setName("test2");
        resourceType2.setDescription("test2");
        resourceType2.setCreatedOn(LocalDateTime.now());
        resourceType2.setEditedOn(LocalDateTime.now());
        resourceType2.setGroupId(1);
    }

    @Test
    public void saveExistingTest() {
        resourceTypeDAO.save(resourceType1);
    }

    @Test
    public void saveNewTest() {
        resourceTypeDAO.save(resourceType1);
    }

    @Test
    public void removeTest() {
        resourceTypeDAO.remove(1);
    }

    @Test
    public void getUsersTest() {
        List<ResourceType> resourceTypeList = resourceTypeDAO.getUsersResourceTypes(user);
        assertEquals(1, resourceTypeList.size());
    }
}
