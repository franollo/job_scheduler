package main.java.controllers;

import main.java.dao.model.*;
import main.java.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Marcin Frankowski
 */

@RestController
@RequestMapping("/remove")
public class RemoveController {

    @Autowired
    private ItemDAO itemDAO;

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductionPlanDAO productionPlanDAO;

    @Autowired
    private ProductOperationDAO productOperationDAO;

    @Autowired
    private ResourceDAO resourceDAO;

    @Autowired
    private ResourceTypeDAO resourceTypeDAO;

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/item", method = RequestMethod.POST)
    public @ResponseBody void item(@RequestBody Item item) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.confirmPermission(Item.class, item.getId(), user);
        itemDAO.remove(item);
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public @ResponseBody void order(@RequestBody Order order) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.confirmPermission(Order.class, order.getId(), user);
        orderDAO.remove(order);
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public @ResponseBody void product(@RequestBody Product product) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.confirmPermission(Product.class, product.getId(), user);
        productDAO.remove(product);
    }

    @RequestMapping(value = "/productionplan", method = RequestMethod.POST)
    public @ResponseBody void productionPlan(@RequestBody ProductionPlan productionPlan) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.confirmPermission(ProductionPlan.class, productionPlan.getId(), user);
        productionPlanDAO.remove(productionPlan);
    }

    @RequestMapping(value = "/productoperation", method = RequestMethod.POST)
    public @ResponseBody void productOperation(@RequestBody ProductOperation productOperation) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.confirmPermission(ProductOperation.class, productOperation.getId(), user);
        productOperationDAO.delete(productOperation);
    }

    @RequestMapping(value = "/resource/{resourceId}", method = RequestMethod.GET)
    public @ResponseBody Integer resource(@PathVariable int resourceId) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        Resource resource = new Resource();
        resource.setId(resourceId);
        userDAO.confirmPermission(Resource.class, resourceId, user);
        resourceDAO.remove(resource);
        return resourceId;
    }

    @RequestMapping(value = "/resourcetype/{resourceTypeId}", method = RequestMethod.GET)
    public @ResponseBody Integer resourceType(@PathVariable int resourceTypeId) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        ResourceType resourceType = new ResourceType();
        resourceType.setId(resourceTypeId);
        userDAO.confirmPermission(ResourceType.class, resourceType.getId(), user);
        resourceTypeDAO.remove(resourceTypeId);
        return resourceTypeId;
    }

    @RequestMapping(value = "/resources", method = RequestMethod.POST)
    public @ResponseBody List<Integer> resources(@RequestBody List<Integer> resourceIds) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.confirmPermission(Resource.class, resourceIds, user);
        resourceDAO.multipleRemove(resourceIds);
        return resourceIds;
    }

    @RequestMapping(value = "/resourcetypes", method = RequestMethod.POST)
    public @ResponseBody List<Integer> resourceTypes(@RequestBody List<Integer> resourceTypeIds) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.confirmPermission(ResourceType.class, resourceTypeIds, user);
        resourceTypeDAO.multipleRemove(resourceTypeIds);
        return resourceTypeIds;
    }


}
