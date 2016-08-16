package main.java.controllers;

import main.java.dao.model.*;
import main.java.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marcin Frankowski
 */

@RestController
@RequestMapping("/save")
public class SaveController {

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
        itemDAO.insert(item);
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public @ResponseBody void order(@RequestBody Order order) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.confirmPermission(Order.class, order.getId(), user);
        orderDAO.insert(order);
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public @ResponseBody Product product(@RequestBody Product product) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.confirmPermission(Product.class, product.getId(), user);
        product.setGroupId(user.getGroupId());
        for(ProductOperation productOperation : product.getProductOperations()) {
            productOperation.setGroupId(user.getGroupId());
        }
        return productDAO.save(product);
    }

    @RequestMapping(value = "/productionplan", method = RequestMethod.POST)
    public @ResponseBody List<Item> productionPlan(@RequestBody ProductionPlan productionPlan) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.confirmPermission(ProductionPlan.class, productionPlan.getId(), user);
        productionPlan.process();
        productionPlanDAO.insert(productionPlan);
        return new ArrayList<>(productionPlan.getItems());
    }

    @RequestMapping(value = "/productoperation", method = RequestMethod.POST)
    public @ResponseBody void productOperation(@RequestBody ProductOperation productOperation) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.confirmPermission(ProductOperation.class, productOperation.getId(), user);
        productOperationDAO.insert(productOperation);
    }

    @RequestMapping(value = "/resource", method = RequestMethod.POST)
    public @ResponseBody Resource resource(@RequestBody Resource resource) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        resource.setGroupId(user.getGroupId());
        userDAO.confirmPermission(Resource.class, resource.getId(), user);
        return resourceDAO.save(resource);
    }

    @RequestMapping(value = "/resourcetype", method = RequestMethod.POST)
    public @ResponseBody ResourceType resourcetype(@RequestBody ResourceType resourceType) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        resourceType.setGroupId(user.getGroupId());
        userDAO.confirmPermission(ResourceType.class, resourceType.getId(), user);
        return resourceTypeDAO.save(resourceType);
    }

    @RequestMapping(value = "/localdatetime", method = RequestMethod.POST)
    public @ResponseBody void resourcetype(@RequestBody LocalDateTime localDateTime) throws ParseException {
        System.out.println(localDateTime);
    }

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public
    @ResponseBody
    void deleteResource() {
        Group group = new Group();
        group.setGroupId(1);
        Product product = new Product();
        ResourceType resourceType = new ResourceType();
        ProductOperation productOperation = new ProductOperation();
        product.setId(1);
        product.setName("test");
        product.setDescription("test");
        resourceType.setId(1);
        resourceType.setName("test");
        productOperation.setDescription("test");
        productOperation.setName("test");
        //productOperation.setDuration(10);
        productOperation.setProductId(1);
        //productOperation.setResourceId(1);
        productOperation.setGroupId(1);
        //productOperationDAO.save(productOperation);
    }
}
