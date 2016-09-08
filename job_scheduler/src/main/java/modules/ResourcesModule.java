package main.java.modules;

import main.java.dao.model.ResourceDAO;
import main.java.dao.model.ResourceTypeDAO;
import main.java.dao.model.UserDAO;
import main.java.model.Resource;
import main.java.model.ResourceType;
import main.java.model.User;

import java.util.List;

/**
 * Module responsible for Resources processing
 * @see main.java.model.Resource
 * @see main.java.model.ResourceType
 * @see main.java.dao.model.UserDAO
 * @see main.java.dao.model.ResourceDAO
 * @see main.java.dao.model.ResourceTypeDAO
 * @author Marcin Frankowski
 */
public class ResourcesModule {
    /**
     * User Data Access Object
     */
    private UserDAO userDAO;

    /**
     * Resource Data Access Object
     */
    private ResourceDAO resourceDAO;

    /**
     * ResourceType Data Access Object
     */
    private ResourceTypeDAO resourceTypeDAO;

    /**
     * Default constructor
     */
    public ResourcesModule() {}

    /**
     * Constructor which sets User DAO, Resource DAO and ResourceType DAO.
     * Module must be initialized with this constructor to work properlly.
     * @param userDAO User Data Access Object
     * @param resourceDAO Resource Data Access Object
     * @param resourceTypeDAO ResourceType Data Access Object
     */
    public ResourcesModule(UserDAO userDAO, ResourceDAO resourceDAO, ResourceTypeDAO resourceTypeDAO) {
        this.userDAO = userDAO;
        this.resourceDAO = resourceDAO;
        this.resourceTypeDAO = resourceTypeDAO;
    }


    /**
     * Save new or existing Resource in system.
     * @param resource resource to save
     * @param user user who wants to save resource
     * @return saved resource
     */
    public Resource saveResource(Resource resource, User user) {
        resource.setGroupId(user.getGroupId());
        userDAO.confirmPermission(Resource.class, resource.getId(), user);
        return resourceDAO.save(resource);
    }


    /**
     * Save new or existing Resource type in system.
     * @param resourceType resource type to save
     * @param user user who wants to save resource type
     * @return saved resource type
     */
    public ResourceType saveResourceType(ResourceType resourceType, User user) {
        resourceType.setGroupId(user.getGroupId());
        userDAO.confirmPermission(ResourceType.class, resourceType.getId(), user);
        return resourceTypeDAO.save(resourceType);
    }

    /**
     * Get all resource types with their resources to which user has permission.
     * @param user user who wants to get resource types
     * @return List of user's resources
     */
    public List<ResourceType> getUserResourceTypes(User user) {
        return resourceTypeDAO.getUsersResourceTypes(user);
    }

    /**
     * Remove resource with id from system.
     * @param resourceId id of resource to remove
     * @param user user who wants to remove resource
     * @return id of removed resource
     */
    public Integer removeResource(Integer resourceId, User user) {
        Resource resource = new Resource();
        resource.setId(resourceId);
        userDAO.confirmPermission(Resource.class, resourceId, user);
        resourceDAO.remove(resource);
        return resourceId;
    }

    /**
     * Remove resources with given list of ids from system.
     * @param resourceIds list of ids of resources to remove
     * @param user user who wants to remove resources
     * @return list of removed resources ids
     */
    public List<Integer> removeResources(List<Integer> resourceIds, User user) {
        userDAO.confirmPermission(Resource.class, resourceIds, user);
        resourceDAO.multipleRemove(resourceIds);
        return resourceIds;
    }

    /**
     * Remove resource type with id from system.
     * @param resourceTypeId of resource type to remove
     * @param user user who wants to remove resource type
     * @return id of removed resource type
     */
    public Integer removeResourceType(Integer resourceTypeId, User user) {
        ResourceType resourceType = new ResourceType();
        resourceType.setId(resourceTypeId);
        userDAO.confirmPermission(ResourceType.class, resourceType.getId(), user);
        resourceTypeDAO.remove(resourceTypeId);
        return resourceTypeId;
    }

    /**
     * Remove resources types with given list of ids from system.
     * @param resourceTypeIds list of ids of resources types to remove
     * @param user user who wants to remove resources types
     * @return list of removed resource types ids
     */
    public List<Integer> removeResourceTypes(List<Integer> resourceTypeIds, User user) {
        userDAO.confirmPermission(ResourceType.class, resourceTypeIds, user);
        resourceTypeDAO.multipleRemove(resourceTypeIds);
        return resourceTypeIds;
    }
}
