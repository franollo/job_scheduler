package main.java.modules;

import main.java.dao.model.ResourceDAO;
import main.java.dao.model.ResourceTypeDAO;
import main.java.dao.model.UserDAO;
import main.java.model.Resource;
import main.java.model.ResourceType;
import main.java.model.User;

import java.util.List;

/**
 * Created by Marcin Frankowski on 06.09.16.
 */
public class ResourcesModule {
    private UserDAO userDAO;
    private ResourceDAO resourceDAO;
    private ResourceTypeDAO resourceTypeDAO;

    public ResourcesModule() {}

    public ResourcesModule(UserDAO userDAO, ResourceDAO resourceDAO, ResourceTypeDAO resourceTypeDAO) {
        this.userDAO = userDAO;
        this.resourceDAO = resourceDAO;
        this.resourceTypeDAO = resourceTypeDAO;
    }

    public Resource saveResource(Resource resource, User user) {
        resource.setGroupId(user.getGroupId());
        userDAO.confirmPermission(Resource.class, resource.getId(), user);
        return resourceDAO.save(resource);
    }

    public ResourceType saveResourceType(ResourceType resourceType, User user) {
        resourceType.setGroupId(user.getGroupId());
        userDAO.confirmPermission(ResourceType.class, resourceType.getId(), user);
        return resourceTypeDAO.save(resourceType);
    }

    public List<ResourceType> getUserResourceTypes(User user) {
        return resourceTypeDAO.getUsersResourceTypes(user);
    }

    public Integer removeResource(Integer resourceId, User user) {
        Resource resource = new Resource();
        resource.setId(resourceId);
        userDAO.confirmPermission(Resource.class, resourceId, user);
        resourceDAO.remove(resource);
        return resourceId;
    }

    public List<Integer> removeResources(List<Integer> resourceIds, User user) {
        userDAO.confirmPermission(Resource.class, resourceIds, user);
        resourceDAO.multipleRemove(resourceIds);
        return resourceIds;
    }

    public Integer removeResourceType(Integer resourceTypeId, User user) {
        ResourceType resourceType = new ResourceType();
        resourceType.setId(resourceTypeId);
        userDAO.confirmPermission(ResourceType.class, resourceType.getId(), user);
        resourceTypeDAO.remove(resourceTypeId);
        return resourceTypeId;
    }

    public List<Integer> removeResourceTypes(List<Integer> resourceTypeIds, User user) {
        userDAO.confirmPermission(ResourceType.class, resourceTypeIds, user);
        resourceTypeDAO.multipleRemove(resourceTypeIds);
        return resourceTypeIds;
    }
}
