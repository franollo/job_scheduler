package main.java.controllers;

import main.java.dao.model.*;
import main.java.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;


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
    public @ResponseBody Item item(@RequestBody Item item) throws ParseException {
        System.out.println("no elo!");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.confirmPermission(Item.class, item.getId(), user);
        itemDAO.save(item);
        return item;
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public @ResponseBody Order order(@RequestBody Order order) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.confirmPermission(Order.class, order.getId(), user);
        order.setGroupId(user.getGroupId());
        order.mergeDuplicatedOrderProducts();
        for(OrderProduct orderProduct : order.getOrderProducts()) {
            orderProduct.setGroupId(user.getGroupId());
        }
        return orderDAO.save(order);
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
        product.orderProductOperations();
        return productDAO.save(product);
    }

    @RequestMapping(value = "/productionplan", method = RequestMethod.POST)
    public @ResponseBody ProductionPlan productionPlan(@RequestBody ProductionPlan productionPlan) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.confirmPermission(ProductionPlan.class, productionPlan.getId(), user);
        productionPlan.setGroupId(user.getGroupId());
        ProductionPlan mergePlan = productionPlanDAO.save(productionPlan);
        mergePlan.process();
        itemDAO.removeByPlanId(mergePlan.getId());
        for(Item item : mergePlan.getItems()) {
            itemDAO.save(item);
        }
        return mergePlan;
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
}
